package com.politechnika.lukasz.services;

import android.content.SharedPreferences;

import com.politechnika.lukasz.models.core.Settings;


public interface ISharedPreferenceHelper {

    void saveSettings(Settings settings);
    Settings getSettings();

    void saveBoolean(String key, boolean value);
    void saveFloat(String key, float value);
    void saveInt(String key, int value);
    void saveString(String key, String value);

    boolean getBoolean(String key, boolean value);
    float getFloat(String key, float value);
    int getInt(String key, int value);
    String getString(String key, String value);

    void setSharedPreferences(SharedPreferences sharedPreferences);
}
