package com.nacoda.moviesmvpdagger2rxjava.main.fragments.popular;


import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.networking.NetworkError;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class PopularPresenter {
    private final Service service;
    private final PopularView view;
    private CompositeSubscription subscriptions;

    public PopularPresenter(Service service, PopularView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getPopularList(int page) {

        view.showWait();
        Subscription subscription = service.getPopularList(new Service.GetPopularCallback() {
            @Override
            public void onSuccess(MoviesListDao moviesListDao) {
                view.removeWait();
                view.getPopularListSuccess(moviesListDao);
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