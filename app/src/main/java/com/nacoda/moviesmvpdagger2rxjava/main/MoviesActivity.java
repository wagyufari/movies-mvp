package com.nacoda.moviesmvpdagger2rxjava.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.appolica.flubber.Flubber;
import com.nacoda.moviesmvpdagger2rxjava.BaseApp;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesPresenter;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesView;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import java.util.Arrays;

import javax.inject.Inject;

public class MoviesActivity extends BaseApp implements MoviesView {

    private RecyclerView rv_movies;

    @Inject
    public Service service;

    SwipeRefreshLayout swipeMovies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);
        renderView();
        init();
        initFlubber();
        initRetrofit();
    }

    public void initRetrofit(){
        final MoviesPresenter presenter = new MoviesPresenter(service, this);
        presenter.getMoviesList(MoviesActivity.this);

        swipeMovies.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getMoviesList(MoviesActivity.this);
            }
        });
    }

    public void initFlubber(){
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_IN)
                .repeatCount(0)
                .duration(300)
                .createFor(findViewById(R.id.rv_movies))
                .start();
    }

    public void renderView() {
        setContentView(R.layout.activity_movies);
        rv_movies = (RecyclerView) findViewById(R.id.rv_movies);
        swipeMovies = (SwipeRefreshLayout) findViewById(R.id.swipeMovies);
    }

    public void init() {
        rv_movies.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void showWait() {
        swipeMovies.setRefreshing(true);
    }

    @Override
    public void removeWait() {
        swipeMovies.setRefreshing(false);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getMoviesListSuccess(MoviesListDao moviesListDao) {

        MoviesAdapter adapter = new MoviesAdapter(getApplicationContext(), moviesListDao,
                new MoviesAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(MoviesApiDao item) {

                        String genres = Arrays.toString(item.getGenre_ids())
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

                        ParcelableMovies movies = new ParcelableMovies(
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

                        Intent detail = new Intent(MoviesActivity.this, DetailActivity.class);

                        detail.putExtra("parcelableMovies", movies);

                        startActivity(detail);


                    }
                });

        rv_movies.setAdapter(adapter);


    }
}