package com.politechnika.lukasz.models.dto.components;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Item {
    private String title;
    @SerializedName("lat")
    private String latitude;
    @SerializedName("long")
    private String longitude;
    private String pubDate;
    private Condition condition;
    private List<ForecastItem> forecast;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public List<ForecastItem> getForecast() {
        return forecast;
    }

    public void setForecast(List<ForecastItem> forecast) {
        this.forecast = forecast;
    }
}
