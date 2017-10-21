package com.politechnika.lukasz.dagger;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import com.politechnika.lukasz.helpers.IPermissionHelper;
import com.politechnika.lukasz.helpers.ISharedPreferenceHelper;
import com.politechnika.lukasz.helpers.PermissionHelper;
import com.politechnika.lukasz.helpers.SharedPreferenceHelper;
import com.politechnika.lukasz.providers.AstroCalculatorProvider;
import com.politechnika.lukasz.providers.IAstroCalculatorProvider;
import com.politechnika.lukasz.validators.IStringInputValidator;
import com.politechnika.lukasz.validators.StringInputValidator;

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

}
