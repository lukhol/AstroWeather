package com.politechnika.lukasz.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.politechnika.lukasz.R;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.models.core.Settings;
import com.politechnika.lukasz.services.ISharedPreferenceHelper;

import javax.inject.Inject;


public class MainInfoFragment extends Fragment {

    @Inject
    Settings settings;

    public MainInfoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DaggerApplication.component().inject(this);

        View view = inflater.inflate(R.layout.fragment_main_info, container, false);
        TextView longLatTextView = view.findViewById(R.id.long_lat_mainTextView);

        String text =
                "Latitude: " +
                String.valueOf(settings.getLatitude()) +
                "\n" +
                "Longitude: " +
                String.valueOf(settings.getLongitude());

        longLatTextView.setText(text);

        return view;
    }
}
