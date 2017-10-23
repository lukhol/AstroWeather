package com.politechnika.lukasz.services;

import com.politechnika.lukasz.exceptions.WeatherInfoException;
import com.politechnika.lukasz.models.core.Weather;

public interface IWeatherService {
    String getWoeidForCity(String city);
    Weather getWeather(String city) throws WeatherInfoException;
}
