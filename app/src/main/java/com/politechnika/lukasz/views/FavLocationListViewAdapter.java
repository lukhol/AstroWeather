package com.politechnika.lukasz.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.politechnika.lukasz.R;
import com.politechnika.lukasz.models.core.Place;

import java.util.ArrayList;

public class FavLocationListViewAdapter extends ArrayAdapter {

    public FavLocationListViewAdapter(Context context, ArrayList<Place> items) {
        super(context, R.layout.location_listview_row, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.location_listview_row, parent, false);

        Place placeItem = (Place)getItem(position);

        TextView textView = customView.findViewById(R.id.locationTextViewOnList);

        textView.setText(placeItem.getCity());

        return customView;
    }
}
