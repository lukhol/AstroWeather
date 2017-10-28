package com.politechnika.lukasz.views.activities;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import com.politechnika.lukasz.R;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.models.core.Place;
import com.politechnika.lukasz.models.core.Settings;
import com.politechnika.lukasz.models.core.Weather;
import com.politechnika.lukasz.services.DBHelper;
import com.politechnika.lukasz.services.ISharedPreferenceHelper;
import com.politechnika.lukasz.services.IWeatherService;
import com.politechnika.lukasz.services.Utils;
import com.politechnika.lukasz.views.fragments.IWeatherListener;
import com.politechnika.lukasz.views.fragments.WeatherFragment;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TestActivity extends BaseActivity implements IWeatherListener{

    @Inject
    Settings settings;

    @Inject
    ISharedPreferenceHelper sharedPreferenceHelper;

    @Inject
    IWeatherService weatherService;

    SwipeRefreshLayout swipeContainer;
    ViewPager mPager;
    ScreenSlidePagerAdapter mPagerAdapter;

    public List<WeatherFragment> listOfWeatherFragments = new ArrayList<>();
    private List<Place> listOfPlaces;
    private WeatherFragment visibleWeatherFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        DaggerApplication.component().inject(this);
        makeToolbarAndActionBar();

        listOfPlaces = getFavouritesFromDatabase();

        mPager = (ViewPager)findViewById(R.id.testViewPager);
        if(mPager != null) {
            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(mPagerAdapter);
            mPager.setOffscreenPageLimit(10);
            mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    visibleWeatherFragment = listOfWeatherFragments.get(position);
                    String cityName = listOfPlaces.get(position).getCity();
                    settings.setActuallyDisplayingCity(cityName);
                    sharedPreferenceHelper.saveSettings(settings);
                    resolveWeatherInformation(getFavouritesFromDatabase());
                    setTitle(cityName);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(false);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    @Override
    public void requestPlace(int position) {
        if(listOfPlaces == null || listOfWeatherFragments == null)
            return;

        if(listOfPlaces.size() - 1 == position){
            for(int i = 0 ; i < listOfPlaces.size() ; i++){
                if(listOfPlaces.get(i).getCity().equals(settings.getActuallyDisplayingCity())){
                    mPager.setCurrentItem(i);
                    break;
                }
            }
        }

        if(listOfPlaces.size() < position || listOfWeatherFragments.size() < position)
            return;


        Place placeToDisplay = listOfPlaces.get(position);

        WeatherFragment senderRequestFragment = listOfWeatherFragments.get(position);
        senderRequestFragment.updatePlace(placeToDisplay);
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

            if(timestampFromDb == null) {
                //Download information and set to the settings
                new GetWeatherAsyncTask().execute(settings.getActuallyDisplayingCity());
                waitingLayout(true, null);
                return;
            }

            long timeBetweenUpdating = Utils.compareTwoTimeStamps(Utils.getCurrentTimeStamp(), timestampFromDb);

            if(timeBetweenUpdating > 15){
                //Download place informaton
                new GetWeatherAsyncTask().execute(settings.getActuallyDisplayingCity());
                waitingLayout(true, null);
                return;
            }

            visibleWeatherFragment.updatePlace(settings.getPlace());
            setTitle(settings.getActuallyDisplayingCity());
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

                    visibleWeatherFragment.updatePlace(settings.getPlace());
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
                fragment.setPosition(position);
                listOfWeatherFragments.add(fragment);
            }
            else{
                fragment = listOfWeatherFragments.get(position);
            }

            return fragment;
        }

        @Override
        public int getCount(){
            return listOfPlaces.size();
        }
    }
}
