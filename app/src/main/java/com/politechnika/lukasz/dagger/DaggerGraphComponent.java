package com.politechnika.lukasz.dagger;

import com.astrocalculator.AstroCalculator;
import com.politechnika.lukasz.astroweather.AstroInfoActivity;
import com.politechnika.lukasz.astroweather.MainActivity;
import com.politechnika.lukasz.astroweather.SettingsActivity;
import com.politechnika.lukasz.astroweather.fragments.MoonFragment;
import com.politechnika.lukasz.astroweather.fragments.SunFragment;
import com.politechnika.lukasz.providers.AstroCalculatorProvider;

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
