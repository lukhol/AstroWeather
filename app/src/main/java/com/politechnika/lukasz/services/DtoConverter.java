package com.politechnika.lukasz.services;

import com.politechnika.lukasz.models.core.Weather;
import com.politechnika.lukasz.models.dto.YahooWeather;
import com.politechnika.lukasz.models.dto.components.Atmosphere;
import com.politechnika.lukasz.models.dto.components.Units;
import com.politechnika.lukasz.models.dto.components.Wind;

public class DtoConverter {
    public Weather dtoYahooWeatherToWeather(YahooWeather yahooWeather){
        Weather weather = new Weather();

        Atmosphere atmosphere = yahooWeather.getQuery().getResult().getChannel().getAtmosphere();
        atmosphere.setPressure(UnitsConverter.inToHpa(atmosphere.getPressure()));


        weather.setAstronomy(yahooWeather.getQuery().getResult().getChannel().getAstronomy());
        weather.setAtmosphere(atmosphere);
        weather.setImage(yahooWeather.getQuery().getResult().getChannel().getImage());
        weather.setWind(yahooWeather.getQuery().getResult().getChannel().getWind());
        weather.setUnits(yahooWeather.getQuery().getResult().getChannel().getUnits());
        weather.setItem(yahooWeather.getQuery().getResult().getChannel().getItem());
        weather.setLocation(yahooWeather.getQuery().getResult().getChannel().getLocation());

        return  weather;
    }
}
