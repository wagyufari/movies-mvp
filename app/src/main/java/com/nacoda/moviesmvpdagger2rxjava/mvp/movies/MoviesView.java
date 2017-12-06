package com.nacoda.moviesmvpdagger2rxjava.mvp.movies;

import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;

/**
 * Created by Mayburger on 10/3/17.
 */

public interface MoviesView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getPopularListSuccess(MoviesListDao moviesListDao);

    void getTopRatedListSuccess(MoviesListDao moviesListDao);

    void getNowPlayingListSuccess(MoviesListDao moviesListDao);

}
