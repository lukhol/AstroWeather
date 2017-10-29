package com.politechnika.lukasz.views;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.politechnika.lukasz.R;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.models.core.Settings;
import com.politechnika.lukasz.models.dto.components.ForecastItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ForecastRecycleViewAdapter extends RecyclerView.Adapter {

    @Inject
    Settings settings;

    private List<ForecastItem> listOfForecast = new ArrayList<>();
    private RecyclerView forecastRecycleView;

    private class ForecastViewHolder extends  RecyclerView.ViewHolder {

        ImageView forecastImageViewRowItem;
        TextView weekDayTextViewRowItem, tempMaxTextViewRowItem, tempMinTextViewRowItem, conditionTextViewRowItem;

        public ForecastViewHolder(View view){
            super(view);
            forecastImageViewRowItem = view.findViewById(R.id.forecastImageViewRowItem);
            weekDayTextViewRowItem = view.findViewById(R.id.weekDayTextViewRowItem);
            tempMaxTextViewRowItem = view.findViewById(R.id.tempMaxTextViewRowItem);
            tempMinTextViewRowItem = view.findViewById(R.id.tempMinTextViewRowItem);
            conditionTextViewRowItem = view.findViewById(R.id.conditionTextViewRowItem);
        }
    }

    public ForecastRecycleViewAdapter(List<ForecastItem> forecastItemsList, RecyclerView recyclerView){
        this.listOfForecast = forecastItemsList;
        this.forecastRecycleView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_listview_row, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ForecastItem forecastItem = listOfForecast.get(position);
        ForecastViewHolder forecastViewHolder = (ForecastViewHolder)holder;

        String stringCode = forecastItem.getCode();
        Resources resources = DaggerApplication.getDaggerApp().getResources();
        final int imageResourceId = resources.getIdentifier("img_" + stringCode, "drawable", DaggerApplication.getDaggerApp().getPackageName());

        forecastViewHolder.forecastImageViewRowItem.setImageResource(imageResourceId);
        forecastViewHolder.weekDayTextViewRowItem.setText(forecastItem.getDay());
        forecastViewHolder.tempMaxTextViewRowItem.setText(forecastItem.getHigh());
        forecastViewHolder.tempMinTextViewRowItem.setText(forecastItem.getLow());
        forecastViewHolder.conditionTextViewRowItem.setText(forecastItem.getText());
    }

    @Override
    public int getItemCount() {
        return listOfForecast.size();
    }
}
