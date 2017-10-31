package com.politechnika.lukasz.views.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.politechnika.lukasz.R;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.models.core.Place;
import com.politechnika.lukasz.models.core.Settings;
import com.politechnika.lukasz.services.UnitsConverter;

import javax.inject.Inject;

public class WeatherBasicFragment extends Fragment implements IWeather {

    @Inject
    Settings settings;

    private TextView temperatureTextView, conditionTextView;
    private ImageView conditionImageView;

    private TextView lastUpdateTimeTextView;

    public WeatherBasicFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_basic, container, false);
        getFieldsById(view);
        return view;
    }

    private void getFieldsById(View view){
        temperatureTextView = view.findViewById(R.id.temperatureTextView);
        conditionTextView = view.findViewById(R.id.conditionTextView);
        conditionImageView = view.findViewById(R.id.conditionImageView);
        lastUpdateTimeTextView = view.findViewById(R.id.lastUpdateTimeTextView);
    }

    @Override
    public void updatePlace(Place place) {
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
        final int imageResourceId = resources.getIdentifier("img_" + stringCode, "drawable", DaggerApplication.getDaggerApp().getPackageName());

        temperatureTextView.setText(temperatureString);
        conditionTextView.setText(conditionString);
        conditionImageView.setImageResource(imageResourceId);

        String lastUpdateTime = place.getLastUpdateTime();
        lastUpdateTime = "Last update: " + lastUpdateTime;

        if(!isOnline())
            lastUpdateTimeTextView.setText(lastUpdateTime.substring(0, lastUpdateTime.length() - 4));
        else
            lastUpdateTimeTextView.setText("");
    }

    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) DaggerApplication.getDaggerApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }
        return false;
    }
}
