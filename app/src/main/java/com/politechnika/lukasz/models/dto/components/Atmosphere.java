package com.politechnika.lukasz.models.dto.components;

/**
 * Created by Lukasz on 22.10.2017.
 */

public class Atmosphere {
    private String humidity;
    private String pressure;
    private String rising;

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getRising() {
        return rising;
    }

    public void setRising(String rising) {
        this.rising = rising;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    private String visibility;


}
