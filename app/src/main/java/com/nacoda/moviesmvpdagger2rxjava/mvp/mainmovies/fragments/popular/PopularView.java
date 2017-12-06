package com.nacoda.moviesmvpdagger2rxjava.mvp.mainmovies.fragments.popular;

import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;

/**
 * Created by Mayburger on 10/3/17.
 */

public interface PopularView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getPopularListSuccess(MoviesListDao moviesListDao);

}
