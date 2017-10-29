package com.politechnika.lukasz.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.politechnika.lukasz.R;
import com.politechnika.lukasz.models.core.Place;

public class WeatherSunInfoFragment extends Fragment implements IWeather {

    private TextView weatherSunriseTextView, weatherSunsetTextView;

    public WeatherSunInfoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_sun_info, container, false);
        getFieldsById(view);
        return view;
    }

    private void getFieldsById(View view){
        weatherSunriseTextView = view.findViewById(R.id.weatherSunriseTextView);
        weatherSunsetTextView = view.findViewById(R.id.weatherSunsetTextView);
    }

    @Override
    public void updatePlace(Place place) {
        if(place == null)
            return;

        weatherSunriseTextView.setText(place.getWeather().getAstronomy().getSunrise());
        weatherSunsetTextView.setText(place.getWeather().getAstronomy().getSunset());
    }
}
