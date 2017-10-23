package com.politechnika.lukasz.web;

import com.politechnika.lukasz.models.core.Weather;
import com.politechnika.lukasz.models.dto.YahooWeather;

/**
 * Created by Lukasz on 23.10.2017.
 */

public class DtoConverter {
    public Weather dtoYahooWeatherToWeather(YahooWeather yahooWeather){
        Weather weather = new Weather();

        weather.setAstronomy(yahooWeather.getQuery().getResult().getChannel().getAstronomy());
        weather.setAtmosphere(yahooWeather.getQuery().getResult().getChannel().getAtmosphere());
        weather.setImage(yahooWeather.getQuery().getResult().getChannel().getImage());
        weather.setWind(yahooWeather.getQuery().getResult().getChannel().getWind());
        weather.setLastBuldDate(yahooWeather.getQuery().getResult().getChannel().getLastBuildDate());
        weather.setUnits(yahooWeather.getQuery().getResult().getChannel().getUnits());
        weather.setItem(yahooWeather.getQuery().getResult().getChannel().getItem());

        return  weather;
    }
}
