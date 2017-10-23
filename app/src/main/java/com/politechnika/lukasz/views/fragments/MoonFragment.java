package com.politechnika.lukasz.views.fragments;
import com.politechnika.lukasz.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.astrocalculator.AstroCalculator;
import com.politechnika.lukasz.R;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.providers.IAstroCalculatorProvider;

import javax.inject.Inject;

public class MoonFragment extends Fragment {
    private TextView moonriseTimeTextView;
    private TextView moonsetTimeTextView;

    private TextView newMoonTextView;
    private TextView fullMoonTextView;

    private TextView moonPhaseTextView;

    private TextView moonAgeTextView;

    @Inject
    IAstroCalculatorProvider astroCalculatorProvider;

    public MoonFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DaggerApplication.component().inject(this);
        View view = inflater.inflate(R.layout.fragment_moon, container, false);

        getFieldsById(view);
        updateFiledsText();

        return view;
    }

    private void getFieldsById(View view){
        moonriseTimeTextView = view.findViewById(R.id.moonriseTimeTextView);
        moonsetTimeTextView = view.findViewById(R.id.moonsetTimeTextView);

        newMoonTextView = view.findViewById(R.id.newMoonTextView);
        fullMoonTextView = view.findViewById(R.id.fullMoonTextView);

        moonPhaseTextView = view.findViewById(R.id.moonPhaseTextView);

        moonAgeTextView = view.findViewById(R.id.moonAgeTextView);
    }

    public void updateFiledsText(){
        AstroCalculator astroCalculator = astroCalculatorProvider.getAstroCalculator();
        AstroCalculator.MoonInfo moonInfo =  astroCalculator.getMoonInfo();

        moonriseTimeTextView.setText("-rise time: " + moonInfo.getMoonrise().toString().substring(10,20));
        moonsetTimeTextView.setText("-set time: " + moonInfo.getMoonset().toString().substring(10,20));

        newMoonTextView.setText("-new moon date: " + moonInfo.getNextNewMoon().toString().substring(0,11));
        fullMoonTextView.setText("-full moon date: " + moonInfo.getNextFullMoon().toString().substring(0,11));

        double moonPhase = Math.round(moonInfo.getIllumination() * 100);

        moonPhaseTextView.setText("-illumination: " + "" + moonPhase + "%");

        moonAgeTextView.setText("-age: " + "" + moonInfo.getAge());
    }
}
