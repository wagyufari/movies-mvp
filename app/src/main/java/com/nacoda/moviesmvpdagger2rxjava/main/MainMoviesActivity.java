package com.nacoda.moviesmvpdagger2rxjava.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.nacoda.moviesmvpdagger2rxjava.BaseActivity;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.main.adapters.MoviesMainAdapter;
import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesPresenter;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesView;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;
import com.nacoda.moviesmvpdagger2rxjava.utils.Gliding;
import com.nacoda.moviesmvpdagger2rxjava.utils.Parcefy;
import com.nacoda.moviesmvpdagger2rxjava.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMoviesActivity extends BaseActivity implements MoviesView {

    @Inject
    public Service service;
    @Inject
    Parcefy parcefy;
    @Inject
    Utils utils;
    @Inject
    Gliding gliding;

    @BindView(R.id.activity_movies_main_rv_popular)
    RecyclerView rvPopular;
    @BindView(R.id.activity_movies_main_rv_toprated)
    RecyclerView rvToprated;
    @BindView(R.id.activity_movies_main_rv_nowplaying)
    RecyclerView rvNowPlaying;
    @BindView(R.id.activity_movies_main_content_linear_layout)
    LinearLayout activityMoviesMainContentLinearLayout;
    @BindView(R.id.activity_movies_main_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayoutMoviesMain;

    String total_pages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
        renderView();
        ButterKnife.bind(this);
        init();
        utils.initFadeinFlubber(swipeRefreshLayoutMoviesMain);
        initRetrofit();

    }

    public void initRetrofit() {
        final MoviesPresenter presenter = new MoviesPresenter(service, this);

        presenter.getPopularList(1);
        presenter.getTopRatedList(1);
        presenter.getNowPlayingList(1);

        swipeRefreshLayoutMoviesMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getPopularList(1);
                presenter.getTopRatedList(1);
                presenter.getNowPlayingList(1);
            }
        });
    }

    public void renderView() {
        setContentView(R.layout.activity_movies_main);
    }

    public void init() {
        LinearLayoutManager popularManager = new LinearLayoutManager(this);
        LinearLayoutManager topRatedManager = new LinearLayoutManager(this);
        LinearLayoutManager nowPlayingManager = new LinearLayoutManager(this);
        popularManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        topRatedManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        nowPlayingManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPopular.setLayoutManager(popularManager);
        rvToprated.setLayoutManager(topRatedManager);
        rvNowPlaying.setLayoutManager(nowPlayingManager);
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
        total_pages = moviesListDao.getTotal_pages();
        initMoviesResponse(moviesListDao, rvPopular);
//        initSlider(moviesListDao);
    }

    @Override
    public void getTopRatedListSuccess(MoviesListDao moviesListDao) {
        initMoviesResponse(moviesListDao, rvToprated);
    }

    @Override
    public void getNowPlayingListSuccess(MoviesListDao moviesListDao) {
        initMoviesResponse(moviesListDao, rvNowPlaying);
        activityMoviesMainContentLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void getMoviesDetailSuccess(DetailApiDao detailApiDao) {

    }

    @Override
    public void getTrailersSuccess(TrailersListDao trailersListDao) {

    }

    @Override
    public void getSimilarSuccess(SimilarListDao similarListDao) {

    }

    public void initMoviesResponse(MoviesListDao moviesListDao, RecyclerView rv_movies) {
        MoviesMainAdapter adapter = new MoviesMainAdapter(getApplicationContext(), moviesListDao,utils,gliding,
                new MoviesMainAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(MoviesApiDao item) {

                        String genres = utils.getGenres(item.getGenre_ids());
                        ParcelableMovies movies = parcefy.fillMoviesParcelable(item, genres);

                        Intent detail = new Intent(MainMoviesActivity.this, DetailActivity.class);
                        detail.putExtra("parcelableMovies", movies);
                        detail.putExtra("id", item.getId());
                        startActivity(detail);
                    }
                });
        rv_movies.setAdapter(adapter);
    }

    @OnClick({R.id.activity_movies_main_popular_see_all_text_view, R.id.activity_movies_main_top_rated_see_all_text_view,R.id.activity_movies_main_now_playing_see_all_text_view})
    public void onClick(View view) {
        Intent movies = new Intent(MainMoviesActivity.this, MoviesActivity.class);
        switch (view.getId()) {
            case R.id.activity_movies_main_popular_see_all_text_view:
                movies.putExtra("total_pages", total_pages);
                movies.putExtra("category", "popular");
                movies.putExtra("page", 1);
                startActivity(movies);
                break;
            case R.id.activity_movies_main_top_rated_see_all_text_view:
                movies.putExtra("total_pages", total_pages);
                movies.putExtra("category", "top_rated");
                movies.putExtra("page", 1);
                startActivity(movies);
                break;
            case R.id.activity_movies_main_now_playing_see_all_text_view:
                movies.putExtra("total_pages", total_pages);
                movies.putExtra("category", "now_playing");
                movies.putExtra("page", 1);
                startActivity(movies);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainMoviesActivity.this, FavoritesActivity.class));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}