package com.nacoda.moviesmvpdagger2rxjava.networking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nacoda.moviesmvpdagger2rxjava.Constants;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.models.CreditsListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;

import java.util.Arrays;

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

    public Subscription getPopularList(final GetPopularCallback callback, int page) {

        return networkService.getPopular(
                Constants.API_KEY,
                "en-US",
                String.valueOf(page)
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

    public Subscription getTopRatedList(final GetTopRatedCallback callback, int page) {

        return networkService.getTopRated(
                Constants.API_KEY,
                "en-US",
                String.valueOf(page)
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

    public Subscription getNowPlayingList(final GetNowPlayingCallback callback, int page) {

        return networkService.getNowPlaying(
                Constants.API_KEY,
                "en-US",
                String.valueOf(page)
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

    public Subscription getMoviesDetail(final GetMoviesDetailCallback callback, String movieId) {

        return networkService.getMoviesDetail(
                movieId,
                Constants.API_KEY,
                "en-US"
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DetailApiDao>>() {
                    @Override
                    public Observable<? extends DetailApiDao> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<DetailApiDao>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(DetailApiDao moviesListDao) {
                        callback.onSuccess(moviesListDao);
                    }
                });


    }

    public Subscription getTrailers(final GetTrailersCallback callback, String movieId) {

        return networkService.getTrailers(
                movieId,
                Constants.API_KEY
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends TrailersListDao>>() {
                    @Override
                    public Observable<? extends TrailersListDao> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<TrailersListDao>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(TrailersListDao trailersApiDao) {
                        callback.onSuccess(trailersApiDao);
                    }
                });


    }

    public Subscription getSimilar(final GetSimilarCallback callback, String movieId) {

        return networkService.getSimilar(
                movieId,
                Constants.API_KEY
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends SimilarListDao>>() {
                    @Override
                    public Observable<? extends SimilarListDao> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<SimilarListDao>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(SimilarListDao similarListDao) {
                        callback.onSuccess(similarListDao);
                    }
                });
    }

    public Subscription getCredits(final GetCreditsCallback callback, String movieId) {

        return networkService.getCredits(
                movieId
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
                    public void onNext(CreditsListDao similarListDao) {
                        callback.onSuccess(similarListDao);
                    }
                });
    }


    public interface GetPopularCallback {
        void onSuccess(MoviesListDao moviesListDao);

        void onError(NetworkError networkError);
    }

    public interface GetMoviesDetailCallback {
        void onSuccess(DetailApiDao detailApiDao);

        void onError(NetworkError networkError);
    }

    public interface GetTrailersCallback {
        void onSuccess(TrailersListDao trailersListDao);

        void onError(NetworkError networkError);
    }

    public interface GetSimilarCallback {
        void onSuccess(SimilarListDao similarListDao);

        void onError(NetworkError networkError);
    }

    public interface GetTopRatedCallback {
        void onSuccess(MoviesListDao moviesListDao);

        void onError(NetworkError networkError);
    }

    public interface GetNowPlayingCallback {
        void onSuccess(MoviesListDao moviesListDao);

        void onError(NetworkError networkError);
    }

    public interface GetCreditsCallback {
        void onSuccess(CreditsListDao creditsListDao);

        void onError(NetworkError networkError);
    }
}
