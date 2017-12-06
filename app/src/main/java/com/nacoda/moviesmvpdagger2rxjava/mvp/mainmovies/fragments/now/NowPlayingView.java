package com.nacoda.moviesmvpdagger2rxjava.mvp.mainmovies.fragments.now;

import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;

/**
 * Created by Mayburger on 10/3/17.
 */

public interface NowPlayingView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getNowPlayingListSuccess(MoviesListDao moviesListDao);

}
