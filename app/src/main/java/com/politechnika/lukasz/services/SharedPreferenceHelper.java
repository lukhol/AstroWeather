package com.politechnika.lukasz.services;

import android.content.SharedPreferences;

import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.models.core.Place;
import com.politechnika.lukasz.models.core.Settings;

public class SharedPreferenceHelper implements ISharedPreferenceHelper {

    SharedPreferences sharedPreferences;

    @Override
    public void saveSettings(Settings settings) {
        saveFloat("longitude", settings.getLongitude());
        saveFloat("latitude", settings.getLatitude());
        saveFloat("refreshTime", settings.getRefreshTime());
        saveString("actuallyDisplayingCity", settings.getActuallyDisplayingCity());
        saveBoolean("kmph", settings.isKmph());
        saveBoolean("celsius", settings.isCelsius());
    }

    @Override
    public Settings getSettings() {
        Settings settings = new Settings();

        float longitude = getFloat("longitude", 0);
        float latitude = getFloat("latitude", 0);
        float refreshTime = getFloat("refreshTime", 10);
        String actuallyDisplayingCity = getString("actuallyDisplayingCity", null);
        boolean kmph = getBoolean("kmph", true);
        boolean celsius = getBoolean("celsius", true);

        settings.setCelsius(celsius);
        settings.setKmph(kmph);
        settings.setActuallyDisplayingCity(actuallyDisplayingCity);
        settings.setLatitude(latitude);
        settings.setLongitude(longitude);
        settings.setRefreshTime(refreshTime);

        return settings;
    }

    @Override
    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    @Override
    public void saveFloat(String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    @Override
    public void saveInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    @Override
    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public boolean getBoolean(String key, boolean value) {
        return sharedPreferences.getBoolean(key, value);
    }

    @Override
    public float getFloat(String key, float value) {
        return sharedPreferences.getFloat(key, value);
    }

    @Override
    public int getInt(String key, int value) {
        return sharedPreferences.getInt(key, value);
    }

    @Override
    public String getString(String key, String value) {
        return sharedPreferences.getString(key, value);
    }

    @Override
    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
}
