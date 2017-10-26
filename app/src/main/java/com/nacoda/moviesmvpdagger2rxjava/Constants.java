package com.nacoda.moviesmvpdagger2rxjava;

import com.nacoda.moviesmvpdagger2rxjava.main.db.FavoritesModel;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;

/**
 * Created by Mayburger on 10/4/17.
 */

public class Constants {

    private Constants() {

    }

    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/w780/";

    public static final String YOUTUBE_API_KEY = "AIzaSyA1nfuspIPxbsrMQNm6RRTt-kfj2J6lsHk";

    public static FavoritesModel FAVORITES_MODEL(ParcelableMovies parcelableMovies) {
        return new FavoritesModel(
                parcelableMovies.getPoster_path(),
                parcelableMovies.getBackdrop_path(),
                parcelableMovies.getTitle(),
                parcelableMovies.getGenres(),
                parcelableMovies.getId(),
                parcelableMovies.getOverview(),
                parcelableMovies.getRelease_date(),
                parcelableMovies.getScore(),
                parcelableMovies.getVote_count()
        );
    }

}
