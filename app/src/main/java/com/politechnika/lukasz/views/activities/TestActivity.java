package com.politechnika.lukasz.views.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import com.politechnika.lukasz.R;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.models.core.Place;
import com.politechnika.lukasz.models.core.Settings;
import com.politechnika.lukasz.views.fragments.IWeatherListener;
import com.politechnika.lukasz.views.fragments.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TestActivity extends BaseActivity implements IWeatherListener{

    @Inject
    Settings settings;

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
                    setTitle(listOfPlaces.get(position).getCity());
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
