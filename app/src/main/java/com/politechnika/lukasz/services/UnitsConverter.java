package com.politechnika.lukasz.services;

public abstract class UnitsConverter {
    public static double celsiusToFahrenheit(double celsius){
        return 32 + (9/5)*celsius;
    }

    public static String celsiusToFahrenheit(String celsius, boolean round){
        try {
            double celsiusValue = Double.parseDouble(celsius);
            double fahrenheitValue = celsiusToFahrenheit(celsiusValue);

            if(round){
                long fahrenheitValueLong = Math.round(fahrenheitValue);
                return String.valueOf(fahrenheitValueLong);
            }

            return String.valueOf(fahrenheitValue);
        } catch (Exception e) {
            return "Unit conversion error.";
        }
    }

    public static double kmphToMph(double kilometers){
        return kilometers * 0.621371192;
    }

    public static String kmphToMph(String kilometers, boolean round){
        try {
            double kilometersValue = Double.parseDouble(kilometers);
            double milesValue = kmphToMph(kilometersValue);

            if(round){
                long milesValueLong = Math.round(milesValue);
                return String.valueOf(milesValueLong);
            }

            return String.valueOf(milesValue);
        } catch (Exception e) {
            return "Unit conversion error.";
        }
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
