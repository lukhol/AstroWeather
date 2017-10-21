package com.politechnika.lukasz.astroweather.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.politechnika.lukasz.astroweather.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MainInfoFragment extends Fragment {

    public MainInfoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_info, container, false);
    }
}
