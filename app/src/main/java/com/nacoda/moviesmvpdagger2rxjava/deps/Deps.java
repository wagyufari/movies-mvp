package com.nacoda.moviesmvpdagger2rxjava.deps;


import com.nacoda.moviesmvpdagger2rxjava.main.DetailActivity;
import com.nacoda.moviesmvpdagger2rxjava.main.MainMoviesActivity;
import com.nacoda.moviesmvpdagger2rxjava.main.MoviesActivity;
import com.nacoda.moviesmvpdagger2rxjava.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Mayburger on 10/3/17.
 */


@Singleton
@Component(modules = {
        NetworkModule.class})
public interface Deps {

    void inject(MainMoviesActivity mainMoviesActivity);

    void inject(MoviesActivity moviesActivity);

    void inject(DetailActivity detailActivity);
}
