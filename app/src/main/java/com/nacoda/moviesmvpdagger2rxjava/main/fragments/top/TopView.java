package com.nacoda.moviesmvpdagger2rxjava.main.fragments.top;

import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;

/**
 * Created by Mayburger on 10/3/17.
 */

public interface TopView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getTopRatedListSuccess(MoviesListDao moviesListDao);

}
