package com.politechnika.lukasz.dagger;

import android.app.Application;
import android.support.v7.widget.Toolbar;

public class DaggerApplication extends Application{
    private static DaggerApplication appInstance;
    private static DaggerGraphComponent graph;

    @Override
    public void onCreate(){
        super.onCreate();
        appInstance = this;
        buildComponentGraph();
    }

    public static DaggerApplication getDaggerApp(){
        return appInstance;
    }

    public static DaggerGraphComponent component() {
        return graph;
    }

    public static void buildComponentGraph() {
        graph = DaggerGraphComponent.Initializer.init(appInstance);
    }
}
