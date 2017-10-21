package com.politechnika.lukasz.helpers;

import android.content.SharedPreferences;

public class SharedPreferenceHelper implements  ISharedPreferenceHelper {

    SharedPreferences sharedPreferences;

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
