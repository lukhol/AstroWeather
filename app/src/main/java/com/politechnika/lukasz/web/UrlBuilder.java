package com.politechnika.lukasz.web;

import android.net.Uri;

public class UrlBuilder {
    private static final String YAHOO_URL_BASE = "https://query.yahooapis.com/v1/public/yql?q=";

    private static final String YAHOO_WOEID_QUERY = "select * from geo.places(1) where text='%s'";
    private static final String YAHOO_WEATHER_QUERY = "select * from weather.forecast where woeid = '%s' and u='c'";
    private static final String YAHOO_WEATHER_ON_CITY_QUERY = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text='%s')";

    public static String getWoeidForCityURL(String cityCode, ResponseType responseType){
        String URL =  YAHOO_URL_BASE + Uri.encode(String.format(YAHOO_WOEID_QUERY, cityCode));
        if(responseType == ResponseType.JSON)
            URL += "&format=json";

        return URL;
    }

    public static String getYahooWeatherCityURL(String city, ResponseType responseType){
        String URL = YAHOO_URL_BASE + Uri.encode(String.format(YAHOO_WEATHER_ON_CITY_QUERY, city));
        if(responseType == ResponseType.JSON)
            URL += "&format=json";

        return URL;
    }

    public static String getYahooWeatherWoeidURL(String woeid, ResponseType responseType){
        String URL = YAHOO_URL_BASE + Uri.encode(String.format(YAHOO_WEATHER_QUERY, woeid));
        if(responseType == ResponseType.JSON)
            URL += "&format=json";

        return URL;
    }

    public enum ResponseType{
        XML, JSON
    }
}
