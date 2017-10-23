package com.politechnika.lukasz.views.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;
import com.politechnika.lukasz.views.fragments.MoonFragment;
import com.politechnika.lukasz.views.fragments.SunFragment;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.helpers.ISharedPreferenceHelper;
import com.politechnika.lukasz.R;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

public class AstroInfoActivity extends BaseActivity {

    @Inject
    ISharedPreferenceHelper sharedPreferenceHelper;

    private static final int NUM_PAGES = 2;
    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;

    private SunFragment sunFragment;
    private MoonFragment moonFragment;

    private static Timer myTimer;
    private static TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerApplication.component().inject(this);
        setContentView(R.layout.activity_astro_info);
        makeToolbarAndActionBar();

        mPager = (ViewPager) findViewById(R.id.pager);
        if(mPager != null) {
            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(mPagerAdapter);
        } else {
            sunFragment = (SunFragment)getSupportFragmentManager().findFragmentById(R.id.firstFragment);
            moonFragment = (MoonFragment)getSupportFragmentManager().findFragmentById(R.id.secondFragment);
        }

        setupTimer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }

    private void setupTimer(){
        String refrehTimeString = sharedPreferenceHelper.getString("refreshTime", "1");
        double refreshTimeDouble = Double.parseDouble(refrehTimeString);

        //To seconds
        refreshTimeDouble *= 1000;
        //To milisecond
        refreshTimeDouble *= 60;

        if(timerTask != null){
            timerTask.cancel();
        }

        myTimer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(moonFragment != null && sunFragment != null) {
                            moonFragment.updateFiledsText();
                            sunFragment.updateFiledsText();
                            Toast.makeText(getApplicationContext(), "Information updated", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Information NOT updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };

        myTimer.schedule(timerTask, (int)refreshTimeDouble, (int)refreshTimeDouble);
    }

    @Override
    public void onBackPressed(){
        if(timerTask != null){
            timerTask.cancel();
            super.onBackPressed();
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position){
            Fragment fragment = null;

            if(position == 0){
                sunFragment = new SunFragment();
                return sunFragment;
            }
            else if(position == 1){
                moonFragment = new MoonFragment();
                return moonFragment;
            }

            return fragment;
        }

        @Override
        public int getCount(){
            return NUM_PAGES;
        }
    }
}
