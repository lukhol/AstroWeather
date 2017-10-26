package com.politechnika.lukasz.services;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Lukasz on 26.10.2017.
 */

public class Utils {
    public static Timestamp getCurrentTimeStamp(){
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        Date now = calendar.getTime();
        return new Timestamp(now.getTime());
    }

    public static long compareTwoTimeStamps(java.sql.Timestamp currentTime, java.sql.Timestamp oldTime)
    {
        long milliseconds1 = oldTime.getTime();
        long milliseconds2 = currentTime.getTime();

        long diff = milliseconds2 - milliseconds1;
        long diffMinutes = diff / (60 * 1000);

        return diffMinutes;
    }
}
