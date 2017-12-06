package com.nacoda.moviesmvpdagger2rxjava.networking;

import com.nacoda.moviesmvpdagger2rxjava.models.CreditsListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;

/**
 * Created by Mayburger on 10/3/17.
 */

public interface NetworkService {

    @GET("movie/popular")
    Observable<MoviesListDao> getPopular(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") String page
    );

    @GET("movie/top_rated")
    Observable<MoviesListDao> getTopRated(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") String page
    );

    @GET("movie/now_playing")
    Observable<MoviesListDao> getNowPlaying(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") String page
    );

    @GET("movie/{movieId}")
    Observable<DetailApiDao> getMoviesDetail(
            @Path("movieId") String movieId,
            @Query("api_key") String api_key,
            @Query("language") String language
    );

    @GET("movie/{movieId}/videos")
    Observable<TrailersListDao> getTrailers(
            @Path("movieId") String movieId,
            @Query("api_key") String api_key
    );

    @GET("movie/{movieId}/similar")
    Observable<SimilarListDao> getSimilar(
            @Path("movieId") String movieId,
            @Query("api_key") String api_key
    );

    @GET("{movieId}/casts")
    Observable<CreditsListDao> getCredits(
            @Path("movieId") String movieId,
            @Path("api_key") String api_key
    );

}
