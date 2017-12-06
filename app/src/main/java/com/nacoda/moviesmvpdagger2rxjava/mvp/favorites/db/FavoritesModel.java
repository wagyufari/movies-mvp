package com.nacoda.moviesmvpdagger2rxjava.mvp.favorites.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class FavoritesModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String poster_path;
    private String backdrop_path;
    private String title;
    private String genres;
    private String movieId;
    private String overview;

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public String getGenres() {
        return genres;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Float getVote_average() {
        return vote_average;
    }

    public Float getVote_count() {
        return vote_count;
    }

    private String release_date;
    private Float vote_average;

    public FavoritesModel(String poster_path, String backdrop_path, String title, String genres, String movieId, String overview, String release_date, Float vote_average, Float vote_count) {
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.title = title;
        this.genres = genres;
        this.movieId = movieId;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    private Float vote_count;





}
