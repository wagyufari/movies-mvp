package com.nacoda.moviesmvpdagger2rxjava.mvp.search;


import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.mvp.movies.MoviesView;
import com.nacoda.moviesmvpdagger2rxjava.networking.NetworkError;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class SearchPresenter {
    private final Service service;
    private final SearchView view;
    private CompositeSubscription subscriptions;

    public SearchPresenter(Service service, SearchView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getSearchResult(int page) {

        view.showWait();
        Subscription subscription = service.getPopularList(new Service.GetPopularCallback() {
            @Override
            public void onSuccess(MoviesListDao moviesListDao) {
                view.removeWait();
                view.getSearchSuccess(moviesListDao);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        }, page);

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}