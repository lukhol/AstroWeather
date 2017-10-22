package com.politechnika.lukasz.models.dto.components;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Item {
    private String title;
    private String lat;
    @SerializedName("long")
    private String longitude;
    private String link;
    private String pubDate;
    private Condition condition;
    private List<ForecastItem> forecast;
    private String description;
    private Guid guid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Guid getGuid() {
        return guid;
    }

    public void setGuid(Guid guid) {
        this.guid = guid;
    }
}
