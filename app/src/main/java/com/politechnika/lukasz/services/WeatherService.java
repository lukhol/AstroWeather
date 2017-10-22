package com.politechnika.lukasz.services;

import com.politechnika.lukasz.models.dto.YahooWeather;
import com.politechnika.lukasz.web.URLs;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService implements IWeatherService {
    public String getWoeidForCity(String city){
        try{
            String stringUrl = URLs.getWoeidForCityURL(city, URLs.ResponseType.JSON);
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

    public YahooWeather getYahooWeather(String woeid){

        return new YahooWeather();
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
