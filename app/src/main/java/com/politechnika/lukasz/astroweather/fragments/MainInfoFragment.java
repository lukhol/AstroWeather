package com.politechnika.lukasz.astroweather.fragments;

<<<<<<< HEAD
=======
import android.content.Context;
import android.net.Uri;
>>>>>>> 8b52c6259456a74ba1fbd3d24345f7dba6a5dd95
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.TextView;
import com.politechnika.lukasz.astroweather.R;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.helpers.ISharedPreferenceHelper;

import javax.inject.Inject;


public class MainInfoFragment extends Fragment {

    @Inject
    ISharedPreferenceHelper sharedPreferenceHelper;

=======

import com.politechnika.lukasz.astroweather.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MainInfoFragment extends Fragment {

>>>>>>> 8b52c6259456a74ba1fbd3d24345f7dba6a5dd95
    public MainInfoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
<<<<<<< HEAD
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
=======
        return inflater.inflate(R.layout.fragment_main_info, container, false);
>>>>>>> 8b52c6259456a74ba1fbd3d24345f7dba6a5dd95
    }
}
