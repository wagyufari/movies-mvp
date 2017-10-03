package com.nacoda.moviesmvpdagger2rxjava.mvp;

import com.nacoda.moviesmvpdagger2rxjava.models.CreditsListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;

/**
 * Created by Mayburger on 10/3/17.
 */

public interface CreditsView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getCreditsListSuccess(CreditsListDao creditsListDao);

}
