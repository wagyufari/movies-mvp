package com.nacoda.moviesmvpdagger2rxjava.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.nacoda.moviesmvpdagger2rxjava.BaseApp;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.models.CreditsListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesPresenter;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesView;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import javax.inject.Inject;

public class MoviesActivity extends BaseApp implements MoviesView {

    private RecyclerView list;
    @Inject
    public Service service;
    SwipeRefreshLayout swipeMovies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);

        renderView();
        init();

        final MoviesPresenter presenter = new MoviesPresenter(service, this);
        presenter.getMoviesList(MoviesActivity.this);

        swipeMovies.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getMoviesList(MoviesActivity.this);
            }
        });
    }

    public void renderView() {
        setContentView(R.layout.activity_movies);
        list = (RecyclerView) findViewById(R.id.rvMovies);
        swipeMovies = (SwipeRefreshLayout) findViewById(R.id.swipeMovies);
    }

    public void init() {
        list.setLayoutManager(new GridLayoutManager(this, 2));
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
                    public void onClick(MoviesApiDao Item) {
                        Toast.makeText(getApplicationContext(), Item.getTitle(),
                                Toast.LENGTH_LONG).show();
                    }
                });

        list.setAdapter(adapter);


    }
}