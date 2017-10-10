package com.nacoda.moviesmvpdagger2rxjava.dagger;


import com.nacoda.moviesmvpdagger2rxjava.dagger.modules.UtilityModule;
import com.nacoda.moviesmvpdagger2rxjava.main.DetailActivity;
import com.nacoda.moviesmvpdagger2rxjava.main.DetailSimilarActivity;
import com.nacoda.moviesmvpdagger2rxjava.main.FavoritesActivity;
import com.nacoda.moviesmvpdagger2rxjava.main.MainMoviesActivity;
import com.nacoda.moviesmvpdagger2rxjava.main.MoviesActivity;
import com.nacoda.moviesmvpdagger2rxjava.main.MoviesPageActivity;
import com.nacoda.moviesmvpdagger2rxjava.dagger.modules.NetworkModule;
import com.nacoda.moviesmvpdagger2rxjava.main.adapters.MoviesAdapter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Mayburger on 10/3/17.
 */


@Singleton
@Component(modules = {
        NetworkModule.class,
        UtilityModule.class
})
public interface ApplicationComponent {

    void inject(DetailActivity detailActivity);
    void inject(DetailSimilarActivity detailSimilarActivity);
    void inject(MainMoviesActivity mainMoviesActivity);
    void inject(MoviesActivity moviesActivity);
    void inject(MoviesPageActivity moviesPageActivity);
    void inject(FavoritesActivity favoritesActivity);

}