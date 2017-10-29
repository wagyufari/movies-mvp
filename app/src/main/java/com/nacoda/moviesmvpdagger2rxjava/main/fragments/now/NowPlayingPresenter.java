package com.nacoda.moviesmvpdagger2rxjava.main.fragments.now;


import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesView;
import com.nacoda.moviesmvpdagger2rxjava.networking.NetworkError;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class NowPlayingPresenter {
    private final Service service;
    private final NowPlayingView view;
    private CompositeSubscription subscriptions;

    public NowPlayingPresenter(Service service, NowPlayingView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getNowPlayingList(int page) {

        view.showWait();
        Subscription subscription = service.getNowPlayingList(new Service.GetNowPlayingCallback() {
            @Override
            public void onSuccess(MoviesListDao moviesListDao) {
                view.removeWait();
                view.getNowPlayingListSuccess(moviesListDao);
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