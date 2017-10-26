package com.politechnika.lukasz.views.fragments;
import com.politechnika.lukasz.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.astrocalculator.AstroCalculator;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.providers.IAstroCalculatorProvider;

import javax.inject.Inject;

public class SunFragment extends Fragment {

    private TextView sunriseTimeTextView;
    private TextView sunriseAzimuthTextView;

    private TextView sunsetTimeTextView;
    private TextView sunsetAzimuthTextView;

    private TextView twilightMorningTextView;
    private TextView twilightEveningTextView;

    @Inject
    IAstroCalculatorProvider astroCalculatorProvider;

    public SunFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DaggerApplication.component().inject(this);
        View view = inflater.inflate(R.layout.fragment_sun, container, false);

        getFieldsById(view);
        updateFiledsText();

        return view;
    }

    private void getFieldsById(View view){
        sunriseTimeTextView = view.findViewById(R.id.sunriseTimeTextView);
        sunriseAzimuthTextView = view.findViewById(R.id.sunriseAzimuthTextView);

        sunsetTimeTextView = view.findViewById(R.id.sunsetTimeTextView);
        sunsetAzimuthTextView = view.findViewById(R.id.sunsetAzimuthTextView);

        twilightMorningTextView = view.findViewById(R.id.twilightMorningTextView);
        twilightEveningTextView = view.findViewById(R.id.twilightEveningTextView);
    }

    public void updateFiledsText(){
        AstroCalculator astroCalculator = astroCalculatorProvider.getAstroCalculator();
        AstroCalculator.SunInfo sunInfo =  astroCalculator.getSunInfo();

        if(sunriseAzimuthTextView == null)
            return;

        sunriseTimeTextView.setText("-time: " + sunInfo.getSunrise().toString().substring(10,20));
        sunriseAzimuthTextView.setText("-azimuth: " + (Math.floor(sunInfo.getAzimuthRise() * 100) / 100) + "°");

        sunsetTimeTextView.setText("-time: " + sunInfo.getSunset().toString().substring(10,20));
        sunsetAzimuthTextView.setText("-azimuth: " + (Math.floor(sunInfo.getAzimuthSet() * 100) / 100) + "°");

        twilightMorningTextView.setText("-morning: " + sunInfo.getTwilightMorning().toString().substring(10,20));
        twilightEveningTextView.setText("-evening: " + sunInfo.getTwilightEvening().toString().substring(10,20));
    }
}
