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

public class WeatherFragment extends Fragment {

    private Place place;

    private TextView temperatureTextView, conditionTextView;
    private TextView pressureTextView, windVelocityTextView, humidityTextView, visibilityTextView;
    private ImageView conditionImageView;

    public WeatherFragment() {}

    public void updatePlace(Place place){
        this.place = place;

        if(temperatureTextView == null || conditionTextView == null || conditionImageView == null){
            Toast.makeText(DaggerApplication.getDaggerApp().getApplicationContext(), "WeatherFragment null fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        temperatureTextView.setText(place.getWeather().getItem().getCondition().getTemp() + "°");
        conditionTextView.setText(place.getWeather().getItem().getCondition().getText());

        String stringCode = place.getWeather().getItem().getCondition().getCode();
        Resources resources = DaggerApplication.getDaggerApp().getResources();
        final int resourceId = resources.getIdentifier("img_" + stringCode, "drawable", DaggerApplication.getDaggerApp().getPackageName());

        conditionImageView.setImageResource(resourceId);

        String pressureString = place.getWeather().getAtmosphere().getPressure();
        int dotIndex = pressureString.indexOf(".");
        pressureString = pressureString.substring(0, dotIndex + 2);

        pressureTextView.setText(pressureString + " hPa");
        humidityTextView.setText(place.getWeather().getAtmosphere().getHumidity() + " %");
        visibilityTextView.setText(place.getWeather().getAtmosphere().getVisibility() + " km");
        windVelocityTextView.setText(place.getWeather().getWind().getSpeed() + " km/h");
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
