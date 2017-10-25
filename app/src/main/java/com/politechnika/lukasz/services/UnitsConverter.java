package com.politechnika.lukasz.services;

/**
 * Created by Lukasz on 24.10.2017.
 */

public abstract class UnitsConverter {
    public static double celsiusToFahrenheit(double celsius){
        return 32 + (9/5)*celsius;
    }

    public static double kmphToMph(double kilometers){
        return kilometers * 0.621371192;
    }
}
