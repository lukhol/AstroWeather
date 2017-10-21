package com.politechnika.lukasz.helpers;

import android.content.SharedPreferences;


public interface ISharedPreferenceHelper {
    public void saveBoolean(String key, boolean value);
    public void saveFloat(String key, float value);
    public void saveInt(String key, int value);
    public void saveString(String key, String value);

    public boolean getBoolean(String key, boolean value);
    public float getFloat(String key, float value);
    public int getInt(String key, int value);
    public String getString(String key, String value);

    public void setSharedPreferences(SharedPreferences sharedPreferences);
}
