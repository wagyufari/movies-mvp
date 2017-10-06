package com.nacoda.moviesmvpdagger2rxjava.dagger.modules;

import com.nacoda.moviesmvpdagger2rxjava.models.Detail;
import com.nacoda.moviesmvpdagger2rxjava.models.Movies;
import com.nacoda.moviesmvpdagger2rxjava.models.Settings;
import com.nacoda.moviesmvpdagger2rxjava.utils.Gliding;
import com.nacoda.moviesmvpdagger2rxjava.utils.Parcefy;
import com.nacoda.moviesmvpdagger2rxjava.utils.Utils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mayburger on 10/5/17.
 */
@Module
public class UtilityModule {

    @Provides
    @Singleton
    public Gliding provideGlidingUtils() {
        return new Gliding();
    }

    @Provides
    @Singleton
    public Parcefy provideParcefyUtils() {
        return new Parcefy();
    }

    @Provides
    @Singleton
    public Utils provideUtilsUtils() {
        return new Utils();
    }

    @Provides
    @Singleton
    public Detail provideDetailUtils(){
        return new Detail();
    }

    @Provides
    @Singleton
    public Movies provideMoviesUtils(){
        return new Movies();
    }

    @Provides
    @Singleton
    public Settings proviteSettingsUtils(){
        return new Settings();
    }

}
