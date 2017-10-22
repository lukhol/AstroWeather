package com.politechnika.lukasz.web;

public class URLs {
    public static final String YAHOO_WOEID_BASE = "https://query.yahooapis.com/v1/public/yql?q=select * from geo.places(1) where text=";
    public static final String YAHOO_WEATHER_BASE = "https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where woeid=";

    public static String getWoeidForCityURL(String cityCode, ResponseType responseType){
        String URL =  YAHOO_WOEID_BASE + "'" + cityCode + "'";
        if(responseType == ResponseType.JSON)
            URL += "&format=json";

        return URL;
    }

    public static String getYahooWeatherURL(String woeid, ResponseType responseType){
        String URL = YAHOO_WEATHER_BASE + woeid + "u='c'";
        if(responseType == ResponseType.JSON)
            URL += "&format=json";

        return URL;
    }

    public enum ResponseType{
        XML, JSON
    }
}
