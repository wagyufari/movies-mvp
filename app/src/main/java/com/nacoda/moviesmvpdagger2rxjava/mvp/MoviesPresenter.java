package com.nacoda.moviesmvpdagger2rxjava.mvp;


import android.content.Context;

import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;
import com.nacoda.moviesmvpdagger2rxjava.networking.NetworkError;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MoviesPresenter {
    private final Service service;
    private final MoviesView view;
    private CompositeSubscription subscriptions;

    public MoviesPresenter(Service service, MoviesView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getPopularList() {

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

        });

        subscriptions.add(subscription);
    }

    public void getTopRatedList() {

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

        });

        subscriptions.add(subscription);
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

    public void onStop() {
        subscriptions.unsubscribe();
    }
}