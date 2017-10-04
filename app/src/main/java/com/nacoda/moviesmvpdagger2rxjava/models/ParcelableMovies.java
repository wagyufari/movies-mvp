package com.nacoda.moviesmvpdagger2rxjava.models;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by ASUS on 25/09/2017.
 */

public class ParcelableMovies implements Parcelable {

    private String poster_path;
    private String backdrop_path;
    private String title;
    private String release_date;
    private String id;
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

    public String getRelease_date() {
        return release_date;
    }

    public String getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getGenres() {
        return genres;
    }

    public float getVote_average() {
        return vote_average;
    }

    public float getVote_count() {
        return vote_count;
    }

    public ParcelableMovies(String poster_path, String backdrop_path, String title, String release_date, String id, String overview, String genres, float vote_average, float vote_count) {

        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.title = title;
        this.release_date = release_date;
        this.id = id;
        this.overview = overview;
        this.genres = genres;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    private String genres;
    private float vote_average;
    private float vote_count;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster_path);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.title);
        dest.writeString(this.release_date);
        dest.writeString(this.id);
        dest.writeString(this.overview);
        dest.writeString(this.genres);
        dest.writeFloat(this.vote_average);
        dest.writeFloat(this.vote_count);
    }

    protected ParcelableMovies(Parcel in) {
        this.poster_path = in.readString();
        this.backdrop_path = in.readString();
        this.title = in.readString();
        this.release_date = in.readString();
        this.id = in.readString();
        this.overview = in.readString();
        this.genres = in.readString();
        this.vote_average = in.readFloat();
        this.vote_count = in.readFloat();
    }

    public static final Parcelable.Creator<ParcelableMovies> CREATOR = new Parcelable.Creator<ParcelableMovies>() {
        @Override
        public ParcelableMovies createFromParcel(Parcel source) {
            return new ParcelableMovies(source);
        }

        @Override
        public ParcelableMovies[] newArray(int size) {
            return new ParcelableMovies[size];
        }
    };
}
