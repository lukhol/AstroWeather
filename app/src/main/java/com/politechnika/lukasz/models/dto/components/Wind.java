package com.politechnika.lukasz.models.dto.components;

/**
 * Created by Lukasz on 22.10.2017.
 */

public class Wind {
    private String chill;
    private String direction;

    public String getChill() {
        return chill;
    }

    public void setChill(String chill) {
        this.chill = chill;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    private String speed;


}
