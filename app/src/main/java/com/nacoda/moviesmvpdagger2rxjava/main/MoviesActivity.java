package com.nacoda.moviesmvpdagger2rxjava.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appolica.flubber.Flubber;
import com.nacoda.moviesmvpdagger2rxjava.BaseApp;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.main.adapters.MoviesAdapter;
import com.nacoda.moviesmvpdagger2rxjava.main.adapters.MoviesMainAdapter;
import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesPresenter;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesView;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MoviesActivity extends BaseApp implements MoviesView {

    @Inject
    public Service service;
    @InjectView(R.id.activity_movies_rv_movies)
    RecyclerView rvMovies;
    @InjectView(R.id.activity_movies_swipe_refresh_layout_movies)
    SwipeRefreshLayout swipeRefreshLayoutMovies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);
        renderView();
        ButterKnife.inject(this);
        init();
        initFlubber();
        initRetrofit();
    }

    public void initRetrofit() {
        final MoviesPresenter presenter = new MoviesPresenter(service, this);

        switch (getIntent().getStringExtra("category")) {
            case "popular":
                presenter.getPopularList();
                swipeRefreshLayoutMovies.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        presenter.getPopularList();
                    }
                });
                break;
            case "top_rated":
                presenter.getTopRatedList();
                swipeRefreshLayoutMovies.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        presenter.getTopRatedList();
                    }
                });
        }
    }

    public void initFlubber() {
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_IN)
                .repeatCount(0)
                .duration(300)
                .createFor(rvMovies)
                .start();
    }

    public void renderView() {
        setContentView(R.layout.activity_movies);
    }

    public void init() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvMovies.setLayoutManager(layoutManager);
    }

    @Override
    public void showWait() {
        swipeRefreshLayoutMovies.setRefreshing(true);
    }

    @Override
    public void removeWait() {
        swipeRefreshLayoutMovies.setRefreshing(false);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getPopularListSuccess(MoviesListDao moviesListDao) {
        initMoviesResponse(moviesListDao, rvMovies);
    }

    @Override
    public void getTopRatedListSuccess(MoviesListDao moviesListDao) {
        initMoviesResponse(moviesListDao, rvMovies);
    }

    @Override
    public void getMoviesDetailSuccess(DetailApiDao detailApiDao) {

    }

    public void initMoviesResponse(MoviesListDao moviesListDao, RecyclerView rv_movies) {
        MoviesAdapter adapter = new MoviesAdapter(getApplicationContext(), moviesListDao,
                new MoviesAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(MoviesApiDao item) {

                        String genres = service.getGenres(item.getGenre_ids());
                        ParcelableMovies movies = service.fillParcelable(item, genres);

                        Intent detail = new Intent(MoviesActivity.this, DetailActivity.class);
                        detail.putExtra("parcelableMovies", movies);
                        detail.putExtra("id", item.getId());
                        startActivity(detail);
                        overridePendingTransition(R.anim.slide_up, R.anim.no_change);


                    }
                }, service);
        rv_movies.setAdapter(adapter);
    }
}