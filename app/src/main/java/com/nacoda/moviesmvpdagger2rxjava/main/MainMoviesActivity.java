package com.nacoda.moviesmvpdagger2rxjava.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.appolica.flubber.Flubber;
import com.bumptech.glide.Glide;
import com.nacoda.moviesmvpdagger2rxjava.BaseApp;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.fonts.RobotoBold;
import com.nacoda.moviesmvpdagger2rxjava.fonts.RobotoLight;
import com.nacoda.moviesmvpdagger2rxjava.fonts.RobotoRegular;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesPresenter;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesView;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainMoviesActivity extends BaseApp implements MoviesView {

    @Inject
    public Service service;
    @InjectView(R.id.movies_main_backdrop_image_view)
    ImageView moviesMainBackdropImageView;
    @InjectView(R.id.movies_main_poster_image_view)
    ImageView moviesMainPosterImageView;
    @InjectView(R.id.movies_main_title_text_view)
    RobotoBold moviesMainTitleTextView;
    @InjectView(R.id.movies_main_release_date_text_view)
    RobotoRegular moviesMainReleaseDateTextView;
    @InjectView(R.id.movies_main_genre_text_view)
    RobotoLight moviesMainGenreTextView;
    @InjectView(R.id.movies_main_vote_average_rating_bar)
    RatingBar moviesMainVoteAverageRatingBar;
    @InjectView(R.id.rv_popular)
    RecyclerView rvPopular;
    @InjectView(R.id.rv_toprated)
    RecyclerView rvToprated;
    @InjectView(R.id.swipeMovies)
    SwipeRefreshLayout swipeMovies;

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

        presenter.getPopularList(MainMoviesActivity.this);
        presenter.getTopRatedList(MainMoviesActivity.this);

        swipeMovies.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getPopularList(MainMoviesActivity.this);
                presenter.getTopRatedList(MainMoviesActivity.this);
            }
        });
    }

    public void initFlubber() {
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_IN)
                .repeatCount(0)
                .duration(300)
                .createFor(findViewById(R.id.rv_popular))
                .start();
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
    public void getPopularListSuccess(MoviesListDao moviesListDao) {
        initMoviesResponse(moviesListDao, rvPopular);

        MoviesApiDao movies = new MoviesApiDao(
                moviesListDao.getResults().get(0).getPoster_path(),
                moviesListDao.getResults().get(0).getBackdrop_path(),
                moviesListDao.getResults().get(0).getTitle(),
                moviesListDao.getResults().get(0).getRelease_date(),
                moviesListDao.getResults().get(0).getId(),
                moviesListDao.getResults().get(0).getOverview(),
                moviesListDao.getResults().get(0).getGenre_ids(),
                moviesListDao.getResults().get(0).getVote_average(),
                moviesListDao.getResults().get(0).getVote_count()
                );
        initMainMoviesHeader(movies);
    }

    @Override
    public void getTopRatedListSuccess(MoviesListDao moviesListDao) {
        initMoviesResponse(moviesListDao, rvToprated);
    }

    public void initMainMoviesHeader(MoviesApiDao moviesApiDao) {
        String IMAGE_URL = "http://image.tmdb.org/t/p/w780/";

        Glide.with(getApplicationContext()).load(IMAGE_URL + moviesApiDao.getBackdrop_path()).crossFade().override(2200, 2000).centerCrop().into(moviesMainBackdropImageView);
        Glide.with(getApplicationContext()).load(IMAGE_URL + moviesApiDao.getPoster_path()).crossFade().centerCrop().into(moviesMainPosterImageView);

        moviesMainTitleTextView.setText(moviesApiDao.getTitle());
        moviesMainReleaseDateTextView.setText(moviesApiDao.getRelease_date());
        moviesMainGenreTextView.setText(getGenres(moviesApiDao.getGenre_ids()));
        moviesMainVoteAverageRatingBar.setRating(moviesApiDao.getVote_average() / 2);
    }

    public void initMoviesResponse(MoviesListDao moviesListDao, RecyclerView rv_movies) {
        MoviesAdapter adapter = new MoviesAdapter(getApplicationContext(), moviesListDao,
                new MoviesAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(MoviesApiDao item) {

                        String genres = getGenres(item.getGenre_ids());

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

                        Intent detail = new Intent(MainMoviesActivity.this, DetailActivity.class);

                        detail.putExtra("parcelableMovies", movies);

                        startActivity(detail);


                    }
                });
        rv_movies.setAdapter(adapter);
    }

    public String getGenres(String[] data) {
        String genres = Arrays.toString(data)
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
        return genres;
    }
}