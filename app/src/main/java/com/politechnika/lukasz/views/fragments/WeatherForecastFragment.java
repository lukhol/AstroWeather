package com.politechnika.lukasz.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.politechnika.lukasz.R;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.models.core.Place;
import com.politechnika.lukasz.models.core.Settings;
import com.politechnika.lukasz.models.dto.components.ForecastItem;
import com.politechnika.lukasz.views.ForecastListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WeatherForecastFragment extends Fragment implements IWeather{

    @Inject
    Settings settings;

    private ListView forecastListView;
    private ForecastListViewAdapter forecastListViewAdapter;
    private List<ForecastItem> listOfForecastItems = new ArrayList<>();

    public WeatherForecastFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        forecastListView = view.findViewById(R.id.forecastListView);
        forecastListViewAdapter = new ForecastListViewAdapter(DaggerApplication.getDaggerApp().getApplicationContext(), listOfForecastItems);
        forecastListView.setAdapter(forecastListViewAdapter);
        forecastListView.setRotation(-90);

        return view;
    }

    @Override
    public void updatePlace(Place place) {
        if(place == null)
            return;

        DaggerApplication.component().inject(this);
        for(ForecastItem forecastItem : place.getWeather().getItem().getForecast()){
            listOfForecastItems.add(forecastItem);
        }

        forecastListViewAdapter.notifyDataSetChanged();
    }
}
