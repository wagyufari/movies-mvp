package com.nacoda.moviesmvpdagger2rxjava.networking;

import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Mayburger on 10/3/17.
 */

public interface NetworkService {

    @GET("popular")
    Observable<MoviesListDao> getMovies(
            @Query("api_key") String api_key,
            @Query("language") String language
    );
}
