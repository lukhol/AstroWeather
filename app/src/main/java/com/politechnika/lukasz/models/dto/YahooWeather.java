package com.politechnika.lukasz.models.dto;

import com.politechnika.lukasz.models.dto.components.Query;

public class YahooWeather {
    private Query query;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}
