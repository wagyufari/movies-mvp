package com.nacoda.moviesmvpdagger2rxjava.dagger;


import com.nacoda.moviesmvpdagger2rxjava.dagger.modules.UtilityModule;
import com.nacoda.moviesmvpdagger2rxjava.main.DetailActivity;
import com.nacoda.moviesmvpdagger2rxjava.main.FavoritesActivity;
import com.nacoda.moviesmvpdagger2rxjava.main.MainMoviesActivity;
import com.nacoda.moviesmvpdagger2rxjava.main.MoviesActivity;
import com.nacoda.moviesmvpdagger2rxjava.dagger.modules.NetworkModule;
import com.nacoda.moviesmvpdagger2rxjava.main.fragments.now.NowPlayingFragment;
import com.nacoda.moviesmvpdagger2rxjava.main.fragments.popular.PopularFragment;
import com.nacoda.moviesmvpdagger2rxjava.main.fragments.top.TopFragment;

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
    void inject(MainMoviesActivity mainMoviesActivity);
    void inject(MoviesActivity moviesActivity);
    void inject(FavoritesActivity favoritesActivity);
    void inject(PopularFragment popularFragment);
    void inject(TopFragment topFragment);
    void inject(NowPlayingFragment nowPlayingFragment);

}