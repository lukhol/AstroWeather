package com.politechnika.lukasz.views.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.politechnika.lukasz.R;
import com.politechnika.lukasz.views.fragments.MoonFragment;
import com.politechnika.lukasz.views.fragments.SunFragment;

public class TestActivity extends BaseActivity {

    SwipeRefreshLayout swipeContainer;
    ViewPager mPager;
    ScreenSlidePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        makeToolbarAndActionBar();

        mPager = (ViewPager)findViewById(R.id.testViewPager);
        if(mPager != null) {
            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(mPagerAdapter);
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

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position){
            Fragment fragment = null;

            if(position == 0){
                fragment = new SunFragment();
            }
            else if(position == 1){
                fragment =  new MoonFragment();
            }

            return fragment;
        }

        @Override
        public int getCount(){
            return 2;
        }
    }
}
