package com.politechnika.lukasz.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.politechnika.lukasz.views.R;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.helpers.ISharedPreferenceHelper;

import javax.inject.Inject;


public class MainInfoFragment extends Fragment {

    @Inject
    ISharedPreferenceHelper sharedPreferenceHelper;

    public MainInfoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DaggerApplication.component().inject(this);

        View view = inflater.inflate(R.layout.fragment_main_info, container, false);
        TextView longLatTextView = view.findViewById(R.id.long_lat_mainTextView);

        String text =
                "Latitude: " +
                sharedPreferenceHelper.getString("latitude", "") +
                "\n" +
                "Longitude: " +
                sharedPreferenceHelper.getString("longitude", "");

        longLatTextView.setText(text);

        return view;
    }
}
