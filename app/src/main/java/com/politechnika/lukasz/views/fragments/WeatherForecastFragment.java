package com.politechnika.lukasz.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.politechnika.lukasz.views.ForecastRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WeatherForecastFragment extends Fragment implements IWeather{

    @Inject
    Settings settings;

    private RecyclerView forecastRecycleView;
    private ForecastRecycleViewAdapter forecastRecycleViewAdapter;

    private List<ForecastItem> listOfForecastItems = new ArrayList<>();

    public WeatherForecastFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        forecastRecycleView = view.findViewById(R.id.forecastRecycleView);
        forecastRecycleView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                DaggerApplication.getDaggerApp().getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        forecastRecycleView.setLayoutManager(linearLayoutManager);

        forecastRecycleViewAdapter = new ForecastRecycleViewAdapter(listOfForecastItems, forecastRecycleView);
        forecastRecycleView.setAdapter(forecastRecycleViewAdapter);

        return view;
    }

    @Override
    public void updatePlace(Place place) {
        if(place == null)
            return;

        DaggerApplication.component().inject(this);

        listOfForecastItems.clear();

        for(ForecastItem forecastItem : place.getWeather().getItem().getForecast()){
            if(!listOfForecastItems.contains(forecastItem))
                listOfForecastItems.add(forecastItem);
        }

        forecastRecycleViewAdapter.notifyDataSetChanged();
    }
}
