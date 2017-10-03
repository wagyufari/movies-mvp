package com.nacoda.moviesmvpdagger2rxjava.mvp;


import android.content.Context;

import com.nacoda.moviesmvpdagger2rxjava.models.CreditsListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.networking.NetworkError;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class CreditsPresenter {
    private final Service service;
    private final CreditsView view;
    private CompositeSubscription subscriptions;

    public CreditsPresenter(Service service, CreditsView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getCreditsList(String movieId) {
        view.showWait();

        Subscription subscription = service.getCreditsList(new Service.GetCreditsCallback() {
            @Override
            public void onSuccess(CreditsListDao creditsListDao) {
                view.removeWait();
                view.getCreditsListSuccess(creditsListDao);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        }, movieId);

        subscriptions.add(subscription);
    }


    public void onStop() {
        subscriptions.unsubscribe();
    }
}