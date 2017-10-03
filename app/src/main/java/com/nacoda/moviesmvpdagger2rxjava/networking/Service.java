package com.nacoda.moviesmvpdagger2rxjava.networking;

import com.nacoda.moviesmvpdagger2rxjava.models.CreditsListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Mayburger on 10/3/17.
 */

public class Service {

    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getMoviesList(final GetMoviesCallback callback) {

        return networkService.getMovies(
                "d1fc10c2bd3bb72bd5ddf8f58a74a1a3",
                "en-US"
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends MoviesListDao>>() {
                    @Override
                    public Observable<? extends MoviesListDao> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<MoviesListDao>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(MoviesListDao moviesListDao) {
                        callback.onSuccess(moviesListDao);
                    }
                });


    }

    public Subscription getCreditsList(final GetCreditsCallback callback, String movieId) {

        return networkService.getCredits(
                movieId,
                "d1fc10c2bd3bb72bd5ddf8f58a74a1a3",
                "en-US"
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends CreditsListDao>>() {
                    @Override
                    public Observable<? extends CreditsListDao> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<CreditsListDao>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(CreditsListDao creditsListDao) {
                        callback.onSuccess(creditsListDao);
                    }
                });


    }

    public interface GetCreditsCallback {
        void onSuccess(CreditsListDao creditsListDao);

        void onError(NetworkError networkError);
    }

    public interface GetMoviesCallback {
        void onSuccess(MoviesListDao moviesListDao);

        void onError(NetworkError networkError);
    }
}
