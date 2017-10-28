package com.politechnika.lukasz.views.fragments;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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

import javax.inject.Inject;

public class WeatherFragment extends Fragment {

    @Inject
    Settings settings;

    private Place place;

    private TextView temperatureTextView, conditionTextView;
    private TextView pressureTextView, windVelocityTextView, humidityTextView, visibilityTextView;
    private ImageView conditionImageView;

    public WeatherFragment() {}

    public void updatePlace(Place place){
        this.place = place;
        DaggerApplication.component().inject(this);

        if(temperatureTextView == null || conditionTextView == null || conditionImageView == null){
            Toast.makeText(DaggerApplication.getDaggerApp().getApplicationContext(), "WeatherFragment null fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        String temperatureString = place.getWeather().getItem().getCondition().getTemp();

        if(!settings.isCelsius())
            temperatureString = UnitsConverter.celsiusToFahrenheit(temperatureString, true);

        temperatureString += "°";

        String conditionString = place.getWeather().getItem().getCondition().getText();

        String stringCode = place.getWeather().getItem().getCondition().getCode();
        Resources resources = DaggerApplication.getDaggerApp().getResources();
        final int resourceId = resources.getIdentifier("img_" + stringCode, "drawable", DaggerApplication.getDaggerApp().getPackageName());

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

        temperatureTextView.setText(temperatureString);
        conditionTextView.setText(conditionString);
        conditionImageView.setImageResource(resourceId);
        pressureTextView.setText(pressureString);
        humidityTextView.setText(humidityString);
        visibilityTextView.setText(visibilityString);
        windVelocityTextView.setText(windVelocity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        getFieldsById(view);
        return view;
    }

    private void getFieldsById(View view){
        temperatureTextView = view.findViewById(R.id.temperatureTextView);
        conditionTextView = view.findViewById(R.id.conditionTextView);

        conditionImageView = view.findViewById(R.id.conditionImageView);

        pressureTextView = view.findViewById(R.id.pressureTextView);
        windVelocityTextView = view.findViewById(R.id.windVelocityTextView);
        humidityTextView = view.findViewById(R.id.humidityTextView);
        visibilityTextView = view.findViewById(R.id.visibilityTextView);
    }

}
