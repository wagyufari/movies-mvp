package com.nacoda.moviesmvpdagger2rxjava.main.fragments.now;

import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;

/**
 * Created by Mayburger on 10/3/17.
 */

public interface NowPlayingView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getNowPlayingListSuccess(MoviesListDao moviesListDao);

}
