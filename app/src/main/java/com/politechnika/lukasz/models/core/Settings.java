package com.politechnika.lukasz.models.core;

public class Settings {
    private float longitude;
    private float latitude;
    private float refreshTime;
    private String actuallyDisplayingCity;
    private boolean kmph;
    private boolean celsius;
    private Place place;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(float refreshTime) {
        this.refreshTime = refreshTime;
    }

    public String getActuallyDisplayingCity() {
        return actuallyDisplayingCity;
    }

    public void setActuallyDisplayingCity(String actuallyDisplayingCity) {
        this.actuallyDisplayingCity = actuallyDisplayingCity;
    }

    public boolean isKmph() {
        return kmph;
    }

    public void setKmph(boolean kmph) {
        this.kmph = kmph;
    }

    public boolean isCelsius() {
        return celsius;
    }

    public void setCelsius(boolean celsius) {
        this.celsius = celsius;
    }
}

