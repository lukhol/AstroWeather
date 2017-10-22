package com.politechnika.lukasz.services;

import com.politechnika.lukasz.models.dto.YahooWeather;

/**
 * Created by Lukasz on 22.10.2017.
 */

public interface IWeatherService {
    String getWoeidForCity(String city);
    YahooWeather getYahooWeather(String woeid);
}
