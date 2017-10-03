package com.nacoda.moviesmvpdagger2rxjava.main;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.databinding.ActivityDetailBinding;
import com.nacoda.moviesmvpdagger2rxjava.models.Movies;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Movies movies = new Movies();
        movies.setTitle("Captain America");
        movies.setGenres("Movie");
        movies.setVote_average(5);
        movies.setRelease_date("hellothere");

        binding.setMovies(movies);

    }
}
