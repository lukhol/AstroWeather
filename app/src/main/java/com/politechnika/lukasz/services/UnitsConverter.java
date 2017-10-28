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

    public static double inToHpa(double in){ return in / 33.86368260427263;}

    public static String inToHpa(String in){
        try{
            double inValue = Double.parseDouble(in);
            double hpaValue = inToHpa(inValue);
            return String.valueOf(hpaValue);
        } catch (Exception e){
            return "Unit conversion error";
        }
    }
}
