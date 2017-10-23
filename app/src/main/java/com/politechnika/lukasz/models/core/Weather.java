package com.politechnika.lukasz.models.core;

import com.politechnika.lukasz.models.dto.components.Astronomy;
import com.politechnika.lukasz.models.dto.components.Atmosphere;
import com.politechnika.lukasz.models.dto.components.Image;
import com.politechnika.lukasz.models.dto.components.Item;
import com.politechnika.lukasz.models.dto.components.Units;
import com.politechnika.lukasz.models.dto.components.Wind;

/**
 * Created by Lukasz on 23.10.2017.
 */

public class Weather {
    private Units units;
    private Wind wind;
    private Atmosphere atmosphere;
    private Astronomy astronomy;
    private Image image;
    private Item item;
    private String lastBuldDate;

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(Atmosphere atmosphere) {
        this.atmosphere = atmosphere;
    }

    public Astronomy getAstronomy() {
        return astronomy;
    }

    public void setAstronomy(Astronomy astronomy) {
        this.astronomy = astronomy;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getLastBuldDate() {
        return lastBuldDate;
    }

    public void setLastBuldDate(String lastBuldDate) {
        this.lastBuldDate = lastBuldDate;
    }
}
