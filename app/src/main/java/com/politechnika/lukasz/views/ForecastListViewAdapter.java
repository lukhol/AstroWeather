package com.politechnika.lukasz.views;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.politechnika.lukasz.R;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.models.core.Place;
import com.politechnika.lukasz.models.dto.components.ForecastItem;

import java.util.ArrayList;
import java.util.List;

public class ForecastListViewAdapter extends ArrayAdapter{
    public ForecastListViewAdapter(Context context, List<ForecastItem> items) {
        super(context, R.layout.location_listview_row, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.forecast_listview_row, parent, false);

        ForecastItem forecastItem = (ForecastItem) getItem(position);

        ImageView forecastImageViewRowItem = customView.findViewById(R.id.forecastImageViewRowItem);
        TextView weekDayTextViewRowItem = customView.findViewById(R.id.weekDayTextViewRowItem);
        TextView tempMaxTextViewRowItem = customView.findViewById(R.id.tempMaxTextViewRowItem);
        TextView tempMinTextViewRowItem = customView.findViewById(R.id.tempMinTextViewRowItem);
        TextView conditionTextViewRowItem = customView.findViewById(R.id.conditionTextViewRowItem);

        String stringCode = forecastItem.getCode();
        Resources resources = DaggerApplication.getDaggerApp().getResources();
        final int imageResourceId = resources.getIdentifier("img_" + stringCode, "drawable", DaggerApplication.getDaggerApp().getPackageName());

        forecastImageViewRowItem.setImageResource(imageResourceId);
        weekDayTextViewRowItem.setText(forecastItem.getDay());
        tempMaxTextViewRowItem.setText(forecastItem.getHigh());
        tempMinTextViewRowItem.setText(forecastItem.getLow());
        conditionTextViewRowItem.setText(forecastItem.getText());

        customView.setRotation(90);

        return customView;
    }
}
