package com.nacoda.moviesmvpdagger2rxjava.main.fragments.popular;

import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;

/**
 * Created by Mayburger on 10/3/17.
 */

public interface PopularView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getPopularListSuccess(MoviesListDao moviesListDao);

}
