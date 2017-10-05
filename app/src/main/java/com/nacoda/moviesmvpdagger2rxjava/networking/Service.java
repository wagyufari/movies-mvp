package com.nacoda.moviesmvpdagger2rxjava.networking;

import android.content.Context;
import android.databinding.BindingAdapter;
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
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;
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

    public void GlideBackdrop(Context context, String url, final View view){
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>(400, 400) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(drawable);
                }
            }
        });
    }

    public Service(NetworkService networkService) {
        this.networkService = networkService;
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

    public Subscription getMoviesDetail(final GetMoviesDetailCallback callback, String movieId) {

        return networkService.getMoviesDetail(
                movieId,
                "d1fc10c2bd3bb72bd5ddf8f58a74a1a3",
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
                "d1fc10c2bd3bb72bd5ddf8f58a74a1a3"
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

    public ParcelableMovies fillParcelable(MoviesApiDao item, String genres) {
        ParcelableMovies parcelableMovies = new ParcelableMovies(
                item.getPoster_path(),
                item.getBackdrop_path(),
                item.getTitle(),
                item.getRelease_date(),
                item.getId(),
                item.getOverview(),
                genres,
                item.getVote_average(),
                item.getVote_count()
        );
        return parcelableMovies;
    }

    public ParcelableMovies fillParcelableList(MoviesListDao item, String genres, int position) {
        ParcelableMovies parcelableMovies = new ParcelableMovies(
                item.getResults().get(position).getPoster_path(),
                item.getResults().get(position).getBackdrop_path(),
                item.getResults().get(position).getTitle(),
                item.getResults().get(position).getRelease_date(),
                item.getResults().get(position).getId(),
                item.getResults().get(position).getOverview(),
                genres,
                item.getResults().get(position).getVote_average(),
                item.getResults().get(position).getVote_count()
        );
        return parcelableMovies;
    }

    public String getGenres(String[] data) {
        String genres = Arrays.toString(data)
                .replace("28", "Action")
                .replace("12", "Adventure")
                .replace("16", "Animation")
                .replace("35", "Comedy")
                .replace("80", "Crime")
                .replace("99", "Documentary")
                .replace("18", "Drama")
                .replace("14", "Fantasy")
                .replace("27", "Horror")
                .replace("10402", "Music")
                .replace("9648", "Mystery")
                .replace("10749", "Romance")
                .replace("878", "Science Fiction")
                .replace("10770", "TV Movie")
                .replace("10752", "War")
                .replace("37", "Western")
                .replace("10751", "Family")
                .replace("53", "Thriller")
                .replace("[", "")
                .replace("]", "");
        return genres;
    }

    public void initFadeinFlubber(View view) {
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_IN)
                .repeatCount(0)
                .duration(300)
                .createFor(view)
                .start();
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

    public interface GetTopRatedCallback {
        void onSuccess(MoviesListDao moviesListDao);

        void onError(NetworkError networkError);
    }
}
