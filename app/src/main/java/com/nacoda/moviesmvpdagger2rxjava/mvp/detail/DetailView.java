package com.nacoda.moviesmvpdagger2rxjava.mvp.detail;

import com.nacoda.moviesmvpdagger2rxjava.models.CreditsListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;

/**
 * Created by Mayburger on 10/3/17.
 */

public interface DetailView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getMoviesDetailSuccess(DetailApiDao detailApiDao);

    void getTrailersSuccess(TrailersListDao trailersListDao);

    void getSimilarSuccess(SimilarListDao similarListDao);

    void getCreditsSuccess(CreditsListDao creditsListDao);

}
