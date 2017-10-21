package com.politechnika.lukasz.redux;

public class ApplicationState {
    private int counter;

    public ApplicationState(int counter){
        this.counter = counter;
    }

    public void setCounter(int counter){
        this.counter = counter;
    }

    public int getCounter(){
        return counter;
    }
}
