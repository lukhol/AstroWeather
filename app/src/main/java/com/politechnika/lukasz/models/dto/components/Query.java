package com.politechnika.lukasz.models.dto.components;

public class Query {
    private int count;
    private String created;
    private String lang;
    private Results results;

    public Results getResult() {
        return results;
    }

    public void setResult(Results results) {
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
