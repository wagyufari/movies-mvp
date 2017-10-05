package com.nacoda.moviesmvpdagger2rxjava.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nacoda.moviesmvpdagger2rxjava.BaseApp;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.main.adapters.MoviesMainAdapter;
import com.nacoda.moviesmvpdagger2rxjava.main.adapters.MoviesSliderAdapter;
import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesPresenter;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesView;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainMoviesActivity extends BaseApp implements MoviesView {

    @Inject
    public Service service;

    private static int currentPage = 0;

    @InjectView(R.id.activity_movies_main_rv_popular)
    RecyclerView rvPopular;
    @InjectView(R.id.activity_movies_main_rv_toprated)
    RecyclerView rvToprated;
    @InjectView(R.id.activity_movies_main_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayoutMoviesMain;
    @InjectView(R.id.activity_movies_main_header_slider_view_pager)
    ViewPager moviesMainHeaderSliderViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);
        renderView();
        ButterKnife.inject(this);
        init();
        service.initFadeinFlubber(swipeRefreshLayoutMoviesMain);
        initRetrofit();

    }

    public void initRetrofit() {
        final MoviesPresenter presenter = new MoviesPresenter(service, this);

        presenter.getPopularList();
        presenter.getTopRatedList();

        swipeRefreshLayoutMoviesMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getPopularList();
                presenter.getTopRatedList();
            }
        });
    }

    private void initSlider(final MoviesListDao moviesListDao) {

        moviesMainHeaderSliderViewPager.setAdapter(new MoviesSliderAdapter(MainMoviesActivity.this, moviesListDao,service));

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 6) {
                    currentPage = 0;
                }
                moviesMainHeaderSliderViewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4500, 4500);
    }

    public void renderView() {
        setContentView(R.layout.activity_movies_main);
    }

    public void init() {
        LinearLayoutManager popularManager = new LinearLayoutManager(this);
        LinearLayoutManager topRatedManager = new LinearLayoutManager(this);
        popularManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        topRatedManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPopular.setLayoutManager(popularManager);
        rvToprated.setLayoutManager(topRatedManager);
    }

    @Override
    public void showWait() {
        swipeRefreshLayoutMoviesMain.setRefreshing(true);
    }

    @Override
    public void removeWait() {
        swipeRefreshLayoutMoviesMain.setRefreshing(false);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getPopularListSuccess(MoviesListDao moviesListDao) {
        initMoviesResponse(moviesListDao, rvPopular);
        initSlider(moviesListDao);
    }

    @Override
    public void getTopRatedListSuccess(MoviesListDao moviesListDao) {
        initMoviesResponse(moviesListDao, rvToprated);
    }

    @Override
    public void getMoviesDetailSuccess(DetailApiDao detailApiDao) {

    }

    public void initMoviesResponse(MoviesListDao moviesListDao, RecyclerView rv_movies) {
        MoviesMainAdapter adapter = new MoviesMainAdapter(getApplicationContext(), moviesListDao,
                new MoviesMainAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(MoviesApiDao item) {

                        String genres = service.getGenres(item.getGenre_ids());
                        ParcelableMovies movies = service.fillParcelable(item, genres);

                        Intent detail = new Intent(MainMoviesActivity.this, DetailActivity.class);
                        detail.putExtra("parcelableMovies", movies);
                        detail.putExtra("id", item.getId());
                        startActivity(detail);
                        overridePendingTransition(R.anim.slide_up, R.anim.no_change);
                    }
                }, service);
        rv_movies.setAdapter(adapter);
    }

    @OnClick({R.id.activity_movies_main_popular_see_all_text_view, R.id.activity_movies_main_top_rated_see_all_text_view})
    public void onClick(View view) {
        Intent movies = new Intent(MainMoviesActivity.this, MoviesActivity.class);
        switch (view.getId()) {
            case R.id.activity_movies_main_popular_see_all_text_view:
                movies.putExtra("category", "popular");
                startActivity(movies);
                break;
            case R.id.activity_movies_main_top_rated_see_all_text_view:
                movies.putExtra("category", "top_rated");
                startActivity(movies);
                break;
        }
    }
}