package com.nacoda.moviesmvpdagger2rxjava.mvp.mainmovies.fragments.top;

import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;

/**
 * Created by Mayburger on 10/3/17.
 */

public interface TopView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getTopRatedListSuccess(MoviesListDao moviesListDao);

}
