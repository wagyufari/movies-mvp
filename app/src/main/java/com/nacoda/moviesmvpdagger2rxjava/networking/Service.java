package com.nacoda.moviesmvpdagger2rxjava.networking;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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

    @BindingAdapter({"bind:imageBackdrop"})
    public static void loadBackdrop(final ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).crossFade().override(2200,2000).centerCrop().into(imageView);
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadPoster(final ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).crossFade().centerCrop().into(imageView);
    }

    public Subscription getPopularList(final GetPopularCallback callback) {

        return networkService.getPopular(
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

    public Subscription getTopRatedList(final GetTopRatedCallback callback) {

        return networkService.getTopRated(
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


    public interface GetPopularCallback {
        void onSuccess(MoviesListDao moviesListDao);

        void onError(NetworkError networkError);
    }

    public interface GetTopRatedCallback {
        void onSuccess(MoviesListDao moviesListDao);

        void onError(NetworkError networkError);
    }
}
