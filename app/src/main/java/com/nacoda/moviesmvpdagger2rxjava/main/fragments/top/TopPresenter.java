package com.nacoda.moviesmvpdagger2rxjava.main.fragments.top;


import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesView;
import com.nacoda.moviesmvpdagger2rxjava.networking.NetworkError;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class TopPresenter {
    private final Service service;
    private final TopView view;
    private CompositeSubscription subscriptions;

    public TopPresenter(Service service, TopView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getTopRatedList(int page) {

        view.showWait();
        Subscription subscription = service.getTopRatedList(new Service.GetTopRatedCallback() {
            @Override
            public void onSuccess(MoviesListDao moviesListDao) {
                view.removeWait();
                view.getTopRatedListSuccess(moviesListDao);
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