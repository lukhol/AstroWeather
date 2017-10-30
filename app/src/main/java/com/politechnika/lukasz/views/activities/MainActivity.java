package com.politechnika.lukasz.views.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.politechnika.lukasz.models.core.Place;
import com.politechnika.lukasz.models.core.Settings;
import com.politechnika.lukasz.models.core.Weather;
import com.politechnika.lukasz.services.DBHelper;
import com.politechnika.lukasz.services.IWeatherService;
import com.politechnika.lukasz.services.Utils;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.services.IPermissionHelper;
import com.politechnika.lukasz.services.ISharedPreferenceHelper;
import com.politechnika.lukasz.R;
import com.politechnika.lukasz.views.fragments.IWeatherListener;
import com.politechnika.lukasz.views.fragments.WeatherFragment;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import javax.inject.Inject;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, IWeatherListener {
    @Inject
    IPermissionHelper permissionHelper;

    @Inject
    ISharedPreferenceHelper sharedPreferenceHelper;

    @Inject
    IWeatherService weatherService;

    @Inject
    Settings settings;

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    private TextView menuLongitudeTextView;
    private TextView menuLatitudeTextView;

    ViewPager mPager;
    ScreenSlidePagerAdapter mPagerAdapter;

    public static List<WeatherFragment> listOfWeatherFragments;
    private List<Place> listOfPlaces;

    private final int REFRESH_TIME  = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DaggerApplication.component().inject(this);

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Navigation:
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerOpened(View view){
                menuLatitudeTextView = (TextView)findViewById(R.id.menuLatitudeTextView);
                menuLongitudeTextView = (TextView)findViewById(R.id.menuLongitudeTextView);

                String latitudeText = "Latitude: " + String.valueOf(settings.getLatitude());
                String longitudeText = "Longitude: " + String.valueOf(settings.getLongitude());

                menuLatitudeTextView.setText(latitudeText);
                menuLongitudeTextView.setText(longitudeText);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Prepare list of database fragments:
        listOfPlaces = getFavouritesFromDatabase();

        if(listOfWeatherFragments == null){
            listOfWeatherFragments = new ArrayList<>();
        } else {
            listOfWeatherFragments.clear();
        }

        //ViewPager:
        mPager = (ViewPager)findViewById(R.id.mainViewPager);
        if(mPager != null) {
            mPager.setCurrentItem(0);
            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(mPagerAdapter);
            mPager.setOffscreenPageLimit(10);
            mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(listOfWeatherFragments.size() > position){

                        String cityName = listOfPlaces.get(position).getCity();
                        settings.setActuallyDisplayingCity(cityName);
                        sharedPreferenceHelper.saveSettings(settings);
                        resolveWeatherInformation(getFavouritesFromDatabase());
                        setTitle(cityName);

                        for(int i = 0 ; i < listOfWeatherFragments.size() ; i++){
                            WeatherFragment updatingFragment = listOfWeatherFragments.get(i);
                            updatingFragment.updatePlace(listOfPlaces.get(updatingFragment.getPosition()));
                        }

                        mPagerAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        if(permissionHelper != null)
            permissionHelper.checkPermission(this);

        createCityMenuItems(listOfPlaces);
        resolveWeatherInformation(listOfPlaces);
    }

    @Override
    public void finish(){
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestPlace(int position) {
        if(listOfPlaces == null || listOfWeatherFragments == null)
            return;

        if(listOfPlaces.size() < position || listOfWeatherFragments.size() < position)
            return;

        Place placeToDisplay = listOfPlaces.get(position);

        WeatherFragment senderRequestFragment = listOfWeatherFragments.get(position);
        senderRequestFragment.updatePlace(placeToDisplay);
    }

    @Override
    protected void onResume(){
        super.onResume();
        listOfPlaces = getFavouritesFromDatabase();
        mPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i < listOfPlaces.size() ; i++){
                    if(listOfPlaces.get(i).getCity().equals(settings.getActuallyDisplayingCity())){
                        mPager.setCurrentItem(i, false);
                    }
                }
            }
        }, 5);
    }

    private void createCityMenuItems(List<Place> listOfLocations){
        Menu menu = navigationView.getMenu();

        int id = 0;

        for(Place tempPlace : listOfLocations){
            menu.add(0, id + 1, id + 1, tempPlace.getCity())
                    .setIcon(R.drawable.ic_place_localizer);
            id++;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();

        if(id == R.id.actionAddCity){
            Intent editFavLocationsActivity = new Intent(getApplicationContext(), EditFavLocationsActivity.class);
            startActivity(editFavLocationsActivity);
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.settingsMenuItem){
            Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settingsIntent);
        }
        else if(id == R.id.viewPagerMenuItem){
            Intent viewPageIntent = new Intent(getApplicationContext(), AstroInfoActivity.class);
            startActivity(viewPageIntent);
        }
        else if(id == R.id.editFavouriteLocationsMenuItem){
            Intent editFavLocationsIntent = new Intent(getApplicationContext(), EditFavLocationsActivity.class);
            startActivity(editFavLocationsIntent);
        }  else {
            resolveWeatherInformationOnMenuItemClicked(item.getTitle().toString());
        }

        item.setChecked(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onSunAndMoonButtonClicked(View view){
        Intent astroInfoActivity = new Intent(this, AstroInfoActivity.class);
        startActivity(astroInfoActivity);
    }

    private void resolveWeatherInformationOnMenuItemClicked(String city){
        settings.setActuallyDisplayingCity(city);
        sharedPreferenceHelper.saveSettings(settings);
        for(int i = 0 ; i < listOfPlaces.size() ; i++){
            if(listOfPlaces.get(i).getCity().equals(city)){
                mPager.setCurrentItem(i);
            }
        }
    }

    private void resolveWeatherInformation(List<Place> listOfFavouriteLocations){
        if(listOfFavouriteLocations == null)
            return;

        if(listOfFavouriteLocations.size() == 0){
            settings.setPlace(null);
            settings.setActuallyDisplayingCity(null);
            sharedPreferenceHelper.saveSettings(settings);
        }

        if(settings.getActuallyDisplayingCity() == null){
            if(!listOfFavouriteLocations.isEmpty()){
                Place firstFavouritePlace = listOfFavouriteLocations.get(0);
                settings.setActuallyDisplayingCity(firstFavouritePlace.getCity());
                settings.setPlace(firstFavouritePlace);
                sharedPreferenceHelper.saveSettings(settings);
            }
        }

        if(settings.getActuallyDisplayingCity() != null){
            //1. Check if information in settings are valid (date).
            //2. If are not valid download it again.
            //3. Display to the user.

            String actuallyDisplayingCityString = settings.getActuallyDisplayingCity();
            Place actuallyDisplayingCityPlace = settings.getPlace();

            if(!actuallyDisplayingCityPlace.getCity().equals(actuallyDisplayingCityString)){
                Place place = getFavouriteFromDatabase(actuallyDisplayingCityString);
                settings.setPlace(place);
                actuallyDisplayingCityPlace = place;
            }

            Timestamp timestampFromDb = null;

            try{
                timestampFromDb = Timestamp.valueOf(actuallyDisplayingCityPlace.getLastUpdateTime());
            } catch (Exception e) {

            }

            for(int i = 0 ; i < listOfFavouriteLocations.size() ; i++){
                if(listOfFavouriteLocations.get(i).getCity().equals(settings.getActuallyDisplayingCity())){
                    setTitle(settings.getActuallyDisplayingCity());
                    //mPager.setCurrentItem(i);
                }
            }

            if(!isOnline()){
                showInformationDialog("No internet connection.", " You are not connected to the internet. Weather information can be old. Connect to the internet and refresh to be up to date!").show();;
                return;
            }

            if(timestampFromDb == null) {
                //Download information and set to the settings
                new GetWeatherAsyncTask().execute(settings.getActuallyDisplayingCity());
                waitingLayout(true, null);
                return;
            }

            long timeBetweenUpdating = Utils.compareTwoTimeStamps(Utils.getCurrentTimeStamp(), timestampFromDb);

            if(timeBetweenUpdating > REFRESH_TIME){
                //Download place informaton
                new GetWeatherAsyncTask().execute(settings.getActuallyDisplayingCity());
                waitingLayout(true, null);
                return;
            }

        }
    }

    private class GetWeatherAsyncTask extends AsyncTask<String, Void, Pair<Weather, String>> {

        @Override
        protected Pair<Weather, String> doInBackground(String... strings) {
            Weather weather = null;
            try{
                weather = weatherService.getWeather(strings[0]);
            } catch (Exception e){
                return new Pair<>(weather, e.getMessage());
            }
            return new Pair<>(weather, null);
        }

        protected void onPostExecute(Pair<Weather, String> weatherPair){
            if(weatherPair != null) {
                Weather weather = weatherPair.first;
                String message = weatherPair.second;

                if(message != null){
                    waitingLayout(false, message);
                    return;
                }

                if(weather != null){
                    Place place = new Place();
                    try {
                        place.setLongitude(Double.parseDouble(weather.getItem().getLongitude()));
                        place.setLatitude(Double.parseDouble(weather.getItem().getLatitude()));
                        place.setCity(weather.getLocation().getCity());
                        place.setLastUpdateTime(Utils.getCurrentTimeStamp().toString());
                        place.setWeather(weather);
                    } catch (Exception e){

                    }

                    settings.setActuallyDisplayingCity(place.getCity());
                    settings.setPlace(place);
                    sharedPreferenceHelper.saveSettings(settings);

                    DBHelper dbHelper = new DBHelper(getActivity());
                    dbHelper.updateFavourite(place);
                    dbHelper.close();

                    listOfPlaces = getFavouritesFromDatabase();

                    for(int i = 0 ; i < listOfWeatherFragments.size() ; i++){
                        listOfWeatherFragments.get(i).updatePlace(listOfPlaces.get(i));
                    }

                    mPagerAdapter.notifyDataSetChanged();

                    setTitle(settings.getActuallyDisplayingCity());
                    waitingLayout(false, null);
                }
            }
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position){
            WeatherFragment fragment;
            if(listOfWeatherFragments.size() < position + 1) {
                fragment = new WeatherFragment();
            }
            else{
                fragment = listOfWeatherFragments.get(position);
            }

            return fragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            WeatherFragment fragment = (WeatherFragment) super.instantiateItem(container, position);

            if(!listOfWeatherFragments.contains(fragment)){
                fragment.setPosition(position);
                fragment.updatePlace(listOfPlaces.get(position));
                listOfWeatherFragments.add(fragment);
            }

            return fragment;
        }

        @Override
        public int getCount(){
            return listOfPlaces.size();
        }
    }
}
