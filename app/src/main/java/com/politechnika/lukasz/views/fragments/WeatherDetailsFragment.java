package com.politechnika.lukasz.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.politechnika.lukasz.R;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.models.core.Place;
import com.politechnika.lukasz.models.core.Settings;
import com.politechnika.lukasz.services.UnitsConverter;

import javax.inject.Inject;

public class WeatherDetailsFragment extends Fragment implements IWeather{

    @Inject
    Settings settings;

    private TextView pressureTextView, windVelocityTextView, humidityTextView, visibilityTextView;

    public WeatherDetailsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_details, container, false);
        getFieldsById(view);
        return view;
    }

    @Override
    public void updatePlace(Place place) {
        DaggerApplication.component().inject(this);

        String pressureString = place.getWeather().getAtmosphere().getPressure();
        int dotIndex = pressureString.indexOf(".");
        pressureString = pressureString.substring(0, dotIndex + 2) + " hPa";

        String humidityString = place.getWeather().getAtmosphere().getHumidity() + " %";
        String visibilityString = place.getWeather().getAtmosphere().getVisibility();
        String windVelocity = place.getWeather().getWind().getSpeed();

        if(settings.isKmph()){
            visibilityString += " km";
            windVelocity += " km/h";
        } else{
            visibilityString = UnitsConverter.celsiusToFahrenheit(visibilityString, true) + " miles";
            windVelocity = UnitsConverter.kmphToMph(windVelocity, true) + " mph";
        }

        pressureTextView.setText(pressureString);
        humidityTextView.setText(humidityString);
        visibilityTextView.setText(visibilityString);
        windVelocityTextView.setText(windVelocity);
    }

    private void getFieldsById(View view){
        pressureTextView = view.findViewById(R.id.pressureTextView);
        windVelocityTextView = view.findViewById(R.id.windVelocityTextView);
        humidityTextView = view.findViewById(R.id.humidityTextView);
        visibilityTextView = view.findViewById(R.id.visibilityTextView);
    }
}
