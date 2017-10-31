package com.politechnika.lukasz.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.politechnika.lukasz.R;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.models.core.Place;
import com.politechnika.lukasz.models.core.Settings;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WeatherFragment extends Fragment {

    @Inject
    Settings settings;

    private int position = -1;
    private IWeatherListener weatherListener;
    private List<IWeather> listOfFragmentComponents = new ArrayList<>();

    public WeatherFragment() {}

    public void setPosition(int position){
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IWeatherListener){
            weatherListener = ((IWeatherListener)context);
        }
    }

    public void updatePlace(Place place){
        DaggerApplication.component().inject(this);

        for(IWeather weatherFragment : listOfFragmentComponents){
            weatherFragment.updatePlace(place);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        IWeather fragmentWeatherDetails = (IWeather)getChildFragmentManager().findFragmentById(R.id.fragmentWeatherDetails);
        IWeather fragmentWeatherBasic = (IWeather)getChildFragmentManager().findFragmentById(R.id.fragmentWeatherBasic);
        IWeather fragmentWeatherForecast = (IWeather)getChildFragmentManager().findFragmentById(R.id.fragmentWeatherForecast);
        IWeather fragmentWeatherSunInfo = (IWeather)getChildFragmentManager().findFragmentById(R.id.fragmentWeatherSunInfo);

        if(!listOfFragmentComponents.contains(fragmentWeatherDetails))
            listOfFragmentComponents.add(fragmentWeatherDetails);

        if(!listOfFragmentComponents.contains(fragmentWeatherBasic))
            listOfFragmentComponents.add(fragmentWeatherBasic);

        if(!listOfFragmentComponents.contains(fragmentWeatherForecast))
            listOfFragmentComponents.add(fragmentWeatherForecast);

        if(!listOfFragmentComponents.contains(fragmentWeatherSunInfo))
            listOfFragmentComponents.add(fragmentWeatherSunInfo);


        LinearLayout containerForWeatherBasicFragment = view.findViewById(R.id.containerForWeatherBasicFragment);
        if(containerForWeatherBasicFragment != null){
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;

            DisplayMetrics dm = getActivity().getApplicationContext().getResources().getDisplayMetrics();
            int densityDpi = dm.densityDpi;
            int px = 56 * (densityDpi / 160);

            height -= px;

            LinearLayout.LayoutParams layout_lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, height);

            containerForWeatherBasicFragment.setLayoutParams(layout_lp);
        }

        if(position >= 0 && weatherListener != null)
            weatherListener.requestPlace(position);

        return view;
    }
}
