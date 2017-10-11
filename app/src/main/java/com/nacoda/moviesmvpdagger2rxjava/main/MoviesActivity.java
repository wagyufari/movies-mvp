package com.nacoda.moviesmvpdagger2rxjava.main;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;
import com.nacoda.moviesmvpdagger2rxjava.BaseApp;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.main.adapters.MoviesAdapter;
import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.Movies;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MoviesActivity extends BaseApp implements MoviesView {

    @Inject
    public Service service;
    @Inject
    Gliding gliding;
    @Inject
    Parcefy parcefy;
    @Inject
    Utils utils;
    @Inject
    Movies movies;


    @InjectView(R.id.activity_movies_rv_movies)
    RecyclerView rvMovies;
    @InjectView(R.id.activity_movies_swipe_refresh_layout_movies)
    SwipeRefreshLayout swipeRefreshLayoutMovies;
    @InjectView(R.id.activity_movies_arrow_left)
    View activityMoviesArrowLeft;
    @InjectView(R.id.activity_movies_arrow_right)
    View activityMoviesArrowRight;
    @InjectView(R.id.activity_movies_pages_text_view)
    TextView activityMoviesPagesTextView;

    int page;
    String category;
    String total_pages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
        renderView();
        ButterKnife.inject(this);

        if (getIntent().getIntExtra("page", 0) == 0) {
            page = getIntent().getIntExtra("page", 0);
        } else {
            page = getIntent().getIntExtra("page", 0);
        }

        category = getIntent().getStringExtra("category");
        total_pages = getIntent().getStringExtra("total_pages");

        activityMoviesPagesTextView.setText(page + " of " + total_pages);

        init();
        initFlubber();
        initRetrofit();
    }

    public void initRetrofit() {
        final MoviesPresenter presenter = new MoviesPresenter(service, this);
        switch (category) {
            case "popular":
                presenter.getPopularList(page);
                swipeRefreshLayoutMovies.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        presenter.getPopularList(page);
                    }
                });
                break;
            case "top_rated":
                presenter.getTopRatedList(page);
                swipeRefreshLayoutMovies.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        presenter.getTopRatedList(page);
                    }
                });
                break;
            case "now_playing":
                presenter.getNowPlayingList(page);
                swipeRefreshLayoutMovies.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        presenter.getNowPlayingList(page);
                    }
                });
        }
    }

    public void initFlubber() {
        Flubber.with().animation(Flubber.AnimationPreset.FADE_IN).repeatCount(0).duration(300).createFor(rvMovies).start();
    }

    public void renderView() {
        setContentView(R.layout.activity_movies);
    }

    public void init() {
        utils.arrowHelper(page, activityMoviesArrowRight, activityMoviesArrowLeft);
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
        if (page == Integer.parseInt(moviesListDao.getTotal_pages())) {
            activityMoviesArrowRight.setVisibility(View.GONE);
        }

        initMoviesResponse(moviesListDao, rvMovies);
    }

    @Override
    public void getTopRatedListSuccess(MoviesListDao moviesListDao) {
        if (page == Integer.parseInt(moviesListDao.getTotal_pages())) {
            activityMoviesArrowRight.setVisibility(View.GONE);
        }

        initMoviesResponse(moviesListDao, rvMovies);
    }

    @Override
    public void getNowPlayingListSuccess(MoviesListDao moviesListDao) {
        if (page == Integer.parseInt(moviesListDao.getTotal_pages())) {
            activityMoviesArrowRight.setVisibility(View.GONE);
        }

        initMoviesResponse(moviesListDao, rvMovies);
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
        MoviesAdapter adapter = new MoviesAdapter(getApplicationContext(), moviesListDao, utils,gliding,
                new MoviesAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(MoviesApiDao item) {

                        String genres = utils.getGenres(item.getGenre_ids());
                        ParcelableMovies movies = parcefy.fillMoviesParcelable(item, genres);

                        Intent detail = new Intent(MoviesActivity.this, DetailActivity.class);
                        detail.putExtra("parcelableMovies", movies);
                        detail.putExtra("id", item.getId());
                        startActivity(detail);
                        overridePendingTransition(R.anim.slide_up, R.anim.no_change);

                    }
                });
        rv_movies.setAdapter(adapter);
    }

    @OnClick({R.id.activity_movies_arrow_left, R.id.activity_movies_arrow_right, R.id.activity_movies_pages_relative_layout})
    public void onClick(View view) {
        Intent movies = new Intent(MoviesActivity.this, MoviesActivity.class);
        switch (view.getId()) {
            case R.id.activity_movies_arrow_left:
                movies.putExtra("category", category);
                movies.putExtra("page", page - 1);
                movies.putExtra("total_pages", total_pages);
                startActivity(movies);
                finish();
                break;
            case R.id.activity_movies_arrow_right:
                movies.putExtra("category", category);
                movies.putExtra("page", page + 1);
                movies.putExtra("total_pages", total_pages);
                startActivity(movies);
                finish();
                break;
            case R.id.activity_movies_pages_relative_layout:
                Dialog jump = new Dialog(this);
                jump.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                jump.setContentView(R.layout.pages_dialog);
                final EditText pagesDialogJumpEditText = (EditText) jump.findViewById(R.id.pages_dialog_jump_edit_text);
                jump.show();

                pagesDialogJumpEditText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        {
                            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                            {
                                switch (i)
                                {
                                    case KeyEvent.KEYCODE_DPAD_CENTER:
                                    case KeyEvent.KEYCODE_ENTER:


                                        if (pagesDialogJumpEditText.getText().toString().isEmpty()){
                                            Toast.makeText(MoviesActivity.this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
                                        } else if (Integer.parseInt(pagesDialogJumpEditText.getText().toString()) > Integer.parseInt(total_pages)){
                                            Toast.makeText(MoviesActivity.this, "Max pages exceeded", Toast.LENGTH_SHORT).show();
                                        } else if (Integer.parseInt(pagesDialogJumpEditText.getText().toString()) <= 0){
                                            Toast.makeText(MoviesActivity.this, "Can't be less than 1", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Intent movies = new Intent(MoviesActivity.this, MoviesActivity.class);
                                            movies.putExtra("category", category);
                                            movies.putExtra("page", Integer.parseInt(pagesDialogJumpEditText.getText().toString()));
                                            movies.putExtra("total_pages", total_pages);
                                            startActivity(movies);
                                            finish();
                                        }

                                        return true;
                                    default:
                                        break;
                                }
                            }
                            return false;
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}