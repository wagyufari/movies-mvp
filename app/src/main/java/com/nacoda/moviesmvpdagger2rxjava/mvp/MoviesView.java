package com.nacoda.moviesmvpdagger2rxjava.mvp;

import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;

/**
 * Created by Mayburger on 10/3/17.
 */

public interface MoviesView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getPopularListSuccess(MoviesListDao moviesListDao);

    void getTopRatedListSuccess(MoviesListDao moviesListDao);

    void getMoviesDetailSuccess(DetailApiDao detailApiDao);

    void getTrailersSuccess(TrailersListDao trailersListDao);

    void getSimilarSuccess(SimilarListDao similarListDao);

}
