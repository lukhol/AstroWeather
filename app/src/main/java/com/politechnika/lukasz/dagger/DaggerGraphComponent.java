package com.politechnika.lukasz.dagger;

import com.politechnika.lukasz.models.core.Weather;
import com.politechnika.lukasz.services.WeatherService;
import com.politechnika.lukasz.views.activities.AstroInfoActivity;
import com.politechnika.lukasz.views.activities.EditFavLocationsActivity;
import com.politechnika.lukasz.views.activities.MainActivity;
import com.politechnika.lukasz.views.activities.SettingsActivity;
import com.politechnika.lukasz.views.activities.TestActivity;
import com.politechnika.lukasz.views.fragments.MainInfoFragment;
import com.politechnika.lukasz.views.fragments.MoonFragment;
import com.politechnika.lukasz.views.fragments.SunFragment;
import com.politechnika.lukasz.providers.AstroCalculatorProvider;
import com.politechnika.lukasz.views.fragments.WeatherFragment;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {MainModule.class})
public interface DaggerGraphComponent {
    void inject(MainActivity mainActivity);
    void inject(SettingsActivity settingsActivity);
    void inject(SunFragment sunFragment);
    void inject(AstroCalculatorProvider astroCalculatorProvider);
    void inject(MoonFragment moonFragment);
    void inject(AstroInfoActivity astroInfoActivity);
    void inject(MainInfoFragment mainInfoFragment);
    void inject(WeatherService weatherService);
    void inject(EditFavLocationsActivity editFavLocationsActivity);
    void inject(WeatherFragment weatherFragment);
    void inject(TestActivity testActivity);

    static final class Initializer {
        private Initializer() {
        }

        public static DaggerGraphComponent init(DaggerApplication app) {
            return DaggerDaggerGraphComponent.builder()
                    .mainModule(new MainModule(app))
                    .build();
        }
    }
}
