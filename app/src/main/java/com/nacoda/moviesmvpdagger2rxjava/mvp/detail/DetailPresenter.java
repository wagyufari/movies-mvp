package com.nacoda.moviesmvpdagger2rxjava.mvp.detail;


import com.nacoda.moviesmvpdagger2rxjava.models.CreditsListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;
import com.nacoda.moviesmvpdagger2rxjava.networking.NetworkError;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class DetailPresenter {
    private final Service service;
    private final DetailView view;
    private CompositeSubscription subscriptions;

    public DetailPresenter(Service service, DetailView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getMoviesDetail(String movieId) {

        view.showWait();
        Subscription subscription = service.getMoviesDetail(new Service.GetMoviesDetailCallback() {
            @Override
            public void onSuccess(DetailApiDao detailApiDao) {
                view.removeWait();
                view.getMoviesDetailSuccess(detailApiDao);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        }, movieId);

        subscriptions.add(subscription);
    }

    public void getTrailers(String movieId) {

        view.showWait();
        Subscription subscription = service.getTrailers(new Service.GetTrailersCallback() {
            @Override
            public void onSuccess(TrailersListDao trailersListDao) {
                view.removeWait();
                view.getTrailersSuccess(trailersListDao);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        }, movieId);

        subscriptions.add(subscription);
    }

    public void getSimilar(String movieId) {

        view.showWait();
        Subscription subscription = service.getSimilar(new Service.GetSimilarCallback() {
            @Override
            public void onSuccess(SimilarListDao similarListDao) {
                view.removeWait();
                view.getSimilarSuccess(similarListDao);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        }, movieId);

        subscriptions.add(subscription);
    }

    public void getCredits(String movieId) {

        view.showWait();
        Subscription subscription = service.getCredits(new Service.GetCreditsCallback() {
            @Override
            public void onSuccess(CreditsListDao creditsListDao) {
                view.removeWait();
                view.getCreditsSuccess(creditsListDao);
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