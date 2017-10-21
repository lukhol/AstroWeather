package com.politechnika.lukasz.providers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.helpers.ISharedPreferenceHelper;
import com.politechnika.lukasz.helpers.SharedPreferenceHelper;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Created by Lukasz on 14.10.2017.
 */

public class AstroCalculatorProvider implements IAstroCalculatorProvider {
    ISharedPreferenceHelper sharedPreferenceHelper;

    @Override
    public AstroCalculator getAstroCalculator() {
        sharedPreferenceHelper = new SharedPreferenceHelper();
        sharedPreferenceHelper.setSharedPreferences(
                PreferenceManager.getDefaultSharedPreferences(
                        DaggerApplication.getDaggerApp().getApplicationContext()
                )
        );

        String latitudeString = sharedPreferenceHelper.getString("latitude", "0");
        String longitudeString = sharedPreferenceHelper.getString("longitude", "0");

        if(latitudeString.equals(""))
            latitudeString = "0";

        if(longitudeString.equals(""))
            longitudeString = "0";

        double latitude = Double.parseDouble(latitudeString);
        double longitude = Double.parseDouble(longitudeString);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        TimeZone offset = calendar.getTimeZone();
        long utc1 = TimeUnit.HOURS.convert(offset.getRawOffset(), TimeUnit.MILLISECONDS);
        long utc2 = TimeUnit.HOURS.convert(offset.getDSTSavings(), TimeUnit.MILLISECONDS);

        AstroCalculator.Location astroLocation = new AstroCalculator.Location(latitude, longitude);
        AstroDateTime astroDateTime = new AstroDateTime(year, month, day, hour, minute, second, (int)(utc1 + utc2), false);
        AstroCalculator astroCalculator = new AstroCalculator(astroDateTime, astroLocation);

        return astroCalculator;
    }
}
