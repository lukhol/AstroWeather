package com.politechnika.lukasz.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.politechnika.lukasz.R;
import java.util.ArrayList;

public class MyListViewAdapter extends ArrayAdapter {

    public MyListViewAdapter(Context context, ArrayList<String> items) {
        super(context, R.layout.location_listview_row, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.location_listview_row, parent, false);

        String item = (String)getItem(position);

        TextView textView = customView.findViewById(R.id.locationTextViewOnList);

        textView.setText(item);

        return customView;
    }
}
