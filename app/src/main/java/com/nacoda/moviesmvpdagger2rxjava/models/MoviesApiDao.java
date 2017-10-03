package com.nacoda.moviesmvpdagger2rxjava.models;

/**
 * Created by ASUS on 25/09/2017.
 */

public class MoviesApiDao extends BaseApiDao {

    private String poster_path;
    private String backdrop_path;
    private String title;
    private String release_date;
    private String id;
    private String overview;
    private String[] genre_ids;
    private float vote_average;
    private float vote_count;

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String[] getGenre_ids() {
        return genre_ids;
    }

    public float getVote_average() {
        return vote_average;
    }

    public float getVote_count() {
        return vote_count;
    }

    public MoviesApiDao(String poster_path, String backdrop_path, String title, String release_date, String id, String overview, String[] genre_ids, float vote_average, float vote_count) {

        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.title = title;
        this.release_date = release_date;
        this.id = id;
        this.overview = overview;
        this.genre_ids = genre_ids;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }


}
