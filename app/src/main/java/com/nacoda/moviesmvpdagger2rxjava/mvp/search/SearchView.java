package com.nacoda.moviesmvpdagger2rxjava.mvp.search;

import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;

/**
 * Created by Mayburger on 10/3/17.
 */

public interface SearchView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getSearchSuccess(MoviesListDao moviesListDao);

}
