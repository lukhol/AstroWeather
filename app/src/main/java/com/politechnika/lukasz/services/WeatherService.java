package com.politechnika.lukasz.services;

import com.google.gson.Gson;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.models.core.Weather;
import com.politechnika.lukasz.models.dto.YahooWeather;
import com.politechnika.lukasz.web.DtoConverter;
import com.politechnika.lukasz.web.UrlBuilder;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.politechnika.lukasz.exceptions.*;
import javax.inject.Inject;


public class WeatherService implements IWeatherService {

    @Inject
    DtoConverter dtoConverter;

    public WeatherService(){
        DaggerApplication.component().inject(this);
    }

    public String getWoeidForCity(String city){
        try{
            String stringUrl = UrlBuilder.getWoeidForCityURL(city, UrlBuilder.ResponseType.XML);
            URL url = new URL(stringUrl);

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();

            if(responseCode != 200)
                return connection.getResponseMessage();

            InputStream inputStream = connection.getInputStream();

            String resultString = streamToString(inputStream);

            JSONObject json = new JSONObject(resultString);
            return json.optString("woeid");

        } catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public Weather getWeather(String city) throws WeatherInfoException{
        try{
            String stringUrl = UrlBuilder.getYahooWeatherCityURL(city, UrlBuilder.ResponseType.JSON);
            URL url = new URL(stringUrl);

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();

            if(responseCode != 200)
                return null;

            InputStream inputStream = connection.getInputStream();

            String resultString = streamToString(inputStream);
            Gson gson = new Gson();
            YahooWeather yahooWeather = gson.fromJson(resultString, YahooWeather.class);

            if(yahooWeather.getQuery().getCount() == 0)
                throw new WeatherInfoException("Not found results for location: " + city);

            return dtoConverter.dtoYahooWeatherToWeather(yahooWeather);

        } catch (Exception e){

            if(e instanceof WeatherInfoException)
                throw new WeatherInfoException(e.getMessage());

            return null;
        }
    }

    private String streamToString(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try{
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e){
            return e.getMessage();
        }

        return stringBuilder.toString();
    }
}
