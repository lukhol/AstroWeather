package com.politechnika.lukasz.views.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.politechnika.lukasz.R;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.models.core.Place;
import com.politechnika.lukasz.models.core.Settings;
import com.politechnika.lukasz.services.UnitsConverter;
import com.politechnika.lukasz.views.activities.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WeatherFragment extends Fragment {

    @Inject
    Settings settings;

    private Place place;
    private int position = -1;
    private IWeatherListener weatherListener;
    private List<IWeather> listOfFragmentComponents = new ArrayList<>();

    public WeatherFragment() {}

    public void setPosition(int position){
        this.position = position;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IWeatherListener){
            weatherListener = ((IWeatherListener)context);
        }
    }

    public void updatePlace(Place place){
        this.place = place;
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

        if(!listOfFragmentComponents.contains(fragmentWeatherDetails))
            listOfFragmentComponents.add(fragmentWeatherDetails);

        if(!listOfFragmentComponents.contains(fragmentWeatherBasic))
            listOfFragmentComponents.add(fragmentWeatherBasic);

        if(!listOfFragmentComponents.contains(fragmentWeatherForecast))
            listOfFragmentComponents.add(fragmentWeatherForecast);

        if(position >= 0 && weatherListener != null)
            weatherListener.requestPlace(position);

        return view;
    }
}
