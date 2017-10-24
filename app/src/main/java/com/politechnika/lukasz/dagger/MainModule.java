package com.politechnika.lukasz.dagger;

import android.app.Application;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.politechnika.lukasz.helpers.IPermissionHelper;
import com.politechnika.lukasz.helpers.ISharedPreferenceHelper;
import com.politechnika.lukasz.helpers.PermissionHelper;
import com.politechnika.lukasz.helpers.SharedPreferenceHelper;
import com.politechnika.lukasz.providers.AstroCalculatorProvider;
import com.politechnika.lukasz.providers.IAstroCalculatorProvider;
import com.politechnika.lukasz.services.IWeatherService;
import com.politechnika.lukasz.services.WeatherService;
import com.politechnika.lukasz.validators.IStringInputValidator;
import com.politechnika.lukasz.validators.StringInputValidator;
import com.politechnika.lukasz.web.DtoConverter;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    DaggerApplication daggerApp;

    public MainModule(DaggerApplication daggerApp){
        this.daggerApp = daggerApp;
    }

    @Provides
    @Singleton
    protected Application provideApplication(){
        return daggerApp;
    }

    @Provides
    protected IPermissionHelper providePermissionHelper(){
        return new PermissionHelper();
    }

    @Provides
    protected IStringInputValidator provideStringInputValidator() { return new StringInputValidator(); }

    @Provides
    protected IAstroCalculatorProvider provdeAstroCalculatorProvider() { return new AstroCalculatorProvider(); }

    @Provides
    protected ISharedPreferenceHelper provideSharedPreferenceHelper() {
        SharedPreferenceHelper sph = new SharedPreferenceHelper();
        sph.setSharedPreferences(PreferenceManager.getDefaultSharedPreferences(daggerApp.getApplicationContext()));
        return sph;
    }

    @Provides
    protected IWeatherService provideWeatherService(){
        return new WeatherService();
    }

    @Provides
    protected DtoConverter provideDtoConverter() { return new DtoConverter(); }

    @Provides
    protected Gson provideGson() { return new Gson(); }
}
