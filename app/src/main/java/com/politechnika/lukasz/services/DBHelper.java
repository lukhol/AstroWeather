package com.politechnika.lukasz.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.gson.Gson;
import com.politechnika.lukasz.models.core.Place;
import com.politechnika.lukasz.models.core.Weather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AstroWeatherDatabase.db";
    private static final String TABLE_NAME = "favouritesCity";
    private static final String COLUMN_ID = "cityId";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_WEATHER_JSON = "weatherJson";
    private static final String COLUMN_LAST_UPDATE_TIME = "lastUpdateTime";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_FAV_STATEMENT =
                            "CREATE TABLE IF NOT EXISTS "+
                            TABLE_NAME +
                            "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                            COLUMN_CITY + " TEXT, " +
                            COLUMN_LATITUDE + " REAL, " +
                            COLUMN_LONGITUDE + " REAL, " +
                            COLUMN_WEATHER_JSON + " TEXT, " +
                            COLUMN_LAST_UPDATE_TIME + " TEXT " + ");";

    private static final String DROP_FAV_STATEMENT = "DROP TABLE IF EXISTS "+ TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FAV_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_FAV_STATEMENT);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public int insertFavourite(Place place){
        try {
            String jsonString = new Gson().toJson(place.getWeather());

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String timeStamp = format.format(calendar.getTime());

            return insertFavourite(place, jsonString, timeStamp);

        } catch (Exception e) {
            return -1;
        }
    }

    public ArrayList<Place> getFavourites(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_NAME, null);

        ArrayList<Place> listOfFavPlaces = new ArrayList<>();

        while(cursor.moveToNext()){
            Place place = new Place();
            place.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            place.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
            place.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)));
            place.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
            place.setLastUpdateTime(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_UPDATE_TIME)));
            place.setWeather(jsonWeatherToWeather(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_JSON))));

            listOfFavPlaces.add(place);
        }

        cursor.close();
        db.close();

        return listOfFavPlaces;
    }

    public boolean removeFavourite(String city){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_CITY + " = ?", new String[] {city}) > 0;
    }

    public Place getFavourite(String city){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + "WHERE " + COLUMN_CITY + " = " + city, null);
        cursor.moveToNext();

        Place place = new Place();
        place.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        place.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
        place.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)));
        place.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
        place.setLastUpdateTime(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_UPDATE_TIME)));
        place.setWeather(jsonWeatherToWeather(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_JSON))));

        return place;
    }

    private int insertFavourite(Place place, String weatherJson, String timeStamp){

        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(COLUMN_CITY, place.getCity());
            values.put(COLUMN_LATITUDE, place.getLatitude());
            values.put(COLUMN_LONGITUDE, place.getLongitude());
            values.put(COLUMN_WEATHER_JSON, weatherJson);
            values.put(COLUMN_LAST_UPDATE_TIME, timeStamp);

            long newRowId = db.insert(TABLE_NAME, null, values);

            return (int)newRowId;
        } catch (Exception e) {
            String str = e.getMessage();
            return -1;
        }
    }

    private Weather jsonWeatherToWeather(String jsonWeather){

        try {
            return new Gson().fromJson(jsonWeather, Weather.class);
        } catch (Exception e) {

        }
        return null;
    }

    private void recreateFavouriteTable(){
        this.getWritableDatabase().execSQL(DROP_FAV_STATEMENT);
        this.getWritableDatabase().execSQL(CREATE_FAV_STATEMENT);
    }
}
