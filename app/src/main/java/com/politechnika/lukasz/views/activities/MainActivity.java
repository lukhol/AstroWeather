package com.politechnika.lukasz.views.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.politechnika.lukasz.models.core.Place;
import com.politechnika.lukasz.models.core.Weather;
import com.politechnika.lukasz.services.DBHelper;
import com.politechnika.lukasz.services.IWeatherService;
import com.politechnika.lukasz.views.fragments.MainInfoFragment;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.helpers.IPermissionHelper;
import com.politechnika.lukasz.helpers.ISharedPreferenceHelper;
import com.politechnika.lukasz.R;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Inject
    IPermissionHelper permissionHelper;

    @Inject
    ISharedPreferenceHelper sharedPreferenceHelper;

    @Inject
    IWeatherService weatherService;

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    private TextView menuLongitudeTextView;
    private TextView menuLatitudeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DaggerApplication.component().inject(this);

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFragment(R.id.fragment_container_one, new MainInfoFragment());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerOpened(View view){
                menuLatitudeTextView = (TextView)findViewById(R.id.menuLatitudeTextView);
                menuLongitudeTextView = (TextView)findViewById(R.id.menuLongitudeTextView);

                menuLatitudeTextView.setText("Latitude: " + sharedPreferenceHelper.getString("latitude", ""));
                menuLongitudeTextView.setText("Longitude: " + sharedPreferenceHelper.getString("longitude", ""));
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(permissionHelper != null)
            permissionHelper.checkPermission(this);

        createCityMenuItems();
    }

    private void createCityMenuItems(){
        Menu menu = navigationView.getMenu();

        ArrayList<Place> listOfLocations = new ArrayList();

        DBHelper dbHelper = new DBHelper(this);
        listOfLocations = dbHelper.getFavourites();
        dbHelper.close();

        int id = 0;

        for(Place tempPlace : listOfLocations){
            menu.add(0, id + 1, id + 1, tempPlace.getCity())
                    .setIcon(R.drawable.ic_place_localizer);
            id++;
        }
    }

    private void setFragment(int layoutId, Fragment fragment){
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(layoutId, fragment);
        fragmentTransaction.commit();
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
        // Handle navigation view item clicks here.
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
}
