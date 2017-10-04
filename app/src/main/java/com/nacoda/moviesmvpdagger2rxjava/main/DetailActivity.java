package com.nacoda.moviesmvpdagger2rxjava.main;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appolica.flubber.Flubber;
import com.nacoda.moviesmvpdagger2rxjava.BaseApp;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.databinding.ActivityDetailBinding;
import com.nacoda.moviesmvpdagger2rxjava.models.Movies;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import javax.inject.Inject;

public class DetailActivity extends BaseApp {

    @Inject
    public Service service;

    ParcelableMovies parcelableMovies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);
        getParcelable();
        initBinding();
        initFlubber();
    }

    public void initFlubber() {
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_IN)
                .repeatCount(0)
                .duration(500)
                .createFor(findViewById(R.id.flubberLayout))
                .start();
    }

    private void initBinding() {
        String IMAGE_URL = "http://image.tmdb.org/t/p/w780/";

        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        Movies movies = new Movies();
        movies.setTitle(parcelableMovies.getTitle());
        movies.setGenres(parcelableMovies.getGenres());
        movies.setVote_average(parcelableMovies.getVote_average() / 2);
        movies.setRelease_date(parcelableMovies.getRelease_date());
        movies.setBackdrop_path(IMAGE_URL + parcelableMovies.getBackdrop_path());
        movies.setPoster_path(IMAGE_URL + parcelableMovies.getPoster_path());
        binding.setMovies(movies);
    }

    private void getParcelable() {
        parcelableMovies = getIntent().getParcelableExtra("parcelableMovies");
    }
}
