package com.nacoda.moviesmvpdagger2rxjava.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nacoda.moviesmvpdagger2rxjava.BaseActivity;
import com.nacoda.moviesmvpdagger2rxjava.Constants;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.main.db.AddFavoritesViewModel;
import com.nacoda.moviesmvpdagger2rxjava.main.db.FavoritesListViewModel;
import com.nacoda.moviesmvpdagger2rxjava.main.db.FavoritesModel;
import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesPresenter;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesView;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;
import com.nacoda.moviesmvpdagger2rxjava.utils.Parcefy;
import com.nacoda.moviesmvpdagger2rxjava.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity implements MoviesView {

    @Inject
    Service service;
    @Inject
    Parcefy parcefy;
    @Inject
    Utils utils;

    // Back button Toolbar
    @BindView(R.id.activity_detail_toolbar)
    Toolbar activityDetailToolbar;
    // Poster CardView
    @BindView(R.id.activity_detail_poster_card)
    CardView activityDetailPosterCard;
    // Poster background RelativeLayout
    @BindView(R.id.activity_detail_background_layout)
    RelativeLayout activityDetailBackgroundLayout;
    // Movie title TextView
    @BindView(R.id.activity_detail_title_text_view)
    TextView activityDetailTitleTextView;
    // Movie year of release TextView
    @BindView(R.id.activity_detail_year_text_view)
    TextView activityDetailYearTextView;
    // Movie score TextView
    @BindView(R.id.activity_detail_score_text)
    TextView activityDetailScoreText;
    // Detail Loading Layout
    @BindView(R.id.activity_detail_loading_layout)
    RelativeLayout activityDetailLoadingLayout;
    // Loading Progressbar
    @BindView(R.id.activity_detail_progress_bar)
    ProgressBar activityDetailProgressBar;
    // Play Trailer Card
    @BindView(R.id.activity_detail_play_trailer_card)
    CardView activityDetailPlayTrailerCard;
    // Favorite Card
    @BindView(R.id.activity_detail_favorite_card)
    CardView activityDetailFavoriteCard;
    // Unfavorite Card
    @BindView(R.id.activity_detail_unfavorite_card)
    CardView activityDetailUnfavoriteCard;
    // Detail ScrollView
    @BindView(R.id.activity_detail_scroll_view)
    ScrollView activityDetailScrollView;
    // Movie Genres TextView
    @BindView(R.id.activity_detail_genres_text)
    TextView activityDetailGenresText;

    ParcelableMovies parcelableMovies;
    FavoritesListViewModel viewModel;
    AddFavoritesViewModel addViewModel;
    MoviesPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        onSetupToolbar(activityDetailToolbar);
        getApplicationComponent().inject(this);
        getParcelable();

        onSetupProgressBar(activityDetailProgressBar);

        initRetrofit(getIntent().getStringExtra("id"),
                service,
                presenter);

        initBackground(this,
                Constants.IMAGE_URL + parcelableMovies.getBackdrop_path(),
                activityDetailBackgroundLayout);

        initPoster(this,
                Constants.IMAGE_URL + parcelableMovies.getPoster_path(),
                activityDetailPosterCard);

        initMovieDescription(
                activityDetailTitleTextView,
                activityDetailYearTextView,
                activityDetailScoreText,
                activityDetailGenresText
        );

        initFavoritesFunction(activityDetailFavoriteCard, activityDetailUnfavoriteCard);
    }

    /**
     * This method is used to set the progressBar color to white
     *
     * @param progressBar The Progressbar that we want to change its color
     */
    private void onSetupProgressBar(ProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
        }
    }

    /**
     * This method is used to set the value of the given TextView according to the selected movie's data
     *
     * @param titleTextView  The TextView used to show the title of the movie
     * @param yearTextView   The TextView used to show the year of release of the movie (For this one, I used the substring(0,4)
     *                       so that the TextView will only show the year of release, that is the first 4 characters of the data
     * @param scoreTextView  The TextView used to show the score or the vote average of the movie
     * @param genresTextView The TextView used to show the genre of the movie
     */
    private void initMovieDescription(TextView titleTextView, TextView yearTextView, TextView scoreTextView, TextView genresTextView) {
        titleTextView.setText(parcelableMovies.getTitle());
        yearTextView.setText(parcelableMovies.getRelease_date().substring(0, 4));
        scoreTextView.setText(String.valueOf((parcelableMovies.getScore())));
        genresTextView.setText(parcelableMovies.getGenres());
    }

    /**
     * This method is used to set the background of the poster card inside the content_detail.xml
     *
     * @param context  The Context of the activity
     * @param url      The URL to the poster
     * @param cardView The View that we want to set the poster as the cardview's background
     *                 <p>
     *                 P.S: This Glide method is using the setForeground because we cannot use setBackground to set the card's background
     */
    private void initPoster(Context context, String url, final CardView cardView) {
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>(400, 400) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    cardView.setForeground(drawable);
                }
            }
        });
    }

    /**
     * This method is used to set the background of the detail activity with the selected movie backdrop
     *
     * @param context The Context of the activity
     * @param url     The URL to the poster
     * @param view    The View that we want to set the poster as the view's background
     */
    private void initBackground(Context context, String url, final View view) {
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>(400, 400) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(drawable);
                }
            }
        });
    }

    /**
     * This method is overrided so that when the backbutton is pressed, it will navigate the user to the previous activity
     **/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * This method is used to make the title disappear and add the back button to the appbar
     *
     * @param toolbar The toolbar used for the backbutton
     */
    public void onSetupToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_back);

        }
    }

    /**
     * This method is used to get the parcelable of movies sent from the previous activity using Intent and fill the
     * global
     */
    private void getParcelable() {
        parcelableMovies = getIntent().getParcelableExtra("parcelableMovies");
    }

    /**
     * This method is used to initialize the Retrofit to the the data from TheMovieDatabase
     * <p>
     * The data that we're going to get is the detail of the selected movie
     *
     * @param id        The id of the selected movie
     * @param service   The Service class which contains the necessary methods to initialize the Retrofit and get the data using Reactivex
     * @param presenter The Presenter class which contains the necessary methods to run the methods inside the Service class
     */
    public void initRetrofit(String id, Service service, MoviesPresenter presenter) {
        presenter = new MoviesPresenter(service, this);
        presenter.getMoviesDetail(id);
        presenter.getTrailers(id);
        presenter.getSimilar(id);
    }

    /**
     * This method is used to initialize the favorites function
     *
     * @param Favorite_Card   The Favorite Card
     * @param Unfavorite_Card The Unfavorite Card
     *                        <p>
     *                        Functions for loop and if else below:
     *                        Loop through the database
     *                        <p>
     *                        If found that the id of the movie from the intent is the same as the one found at the database, then
     *                        that means the user already favorited that movie and the FavoriteCard will be gone and the UnfavoriteCard will be visible
     *                        <p>
     *                        If not, then that means the user haven't favorited that movie yet and the FavoriteCard will be visible and
     *                        the UnfavoriteCard will be gone
     */
    private void initFavoritesFunction(final CardView Favorite_Card, final CardView Unfavorite_Card) {
        viewModel = ViewModelProviders.of(this).get(FavoritesListViewModel.class);
        addViewModel = ViewModelProviders.of(this).get(AddFavoritesViewModel.class);

        viewModel.getFavoritesList().observe(DetailActivity.this, new Observer<List<FavoritesModel>>() {
            @Override
            public void onChanged(@Nullable List<FavoritesModel> favorites) {
                assert favorites != null;
                for (int i = 0; i < favorites.size(); i++) {
                    if (favorites.get(i).getMovieId().equals(parcelableMovies.getId())) {
                        final FavoritesModel favoritesModel = (FavoritesModel) favorites.get(i);
                        Favorite_Card.setVisibility(View.GONE);
                        Unfavorite_Card.setVisibility(View.VISIBLE);
                        Unfavorite_Card.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(activityDetailScrollView, "Removed from favorites!", Snackbar.LENGTH_SHORT).show();
                                viewModel.deleteFavorites(favoritesModel);
                                Unfavorite_Card.setVisibility(View.GONE);
                                Favorite_Card.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            }
        });

        Favorite_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(activityDetailScrollView, "Added to favorites!", Snackbar.LENGTH_SHORT).show();
                addViewModel.addFavorites(Constants.FAVORITES_MODEL(parcelableMovies));
                Favorite_Card.setVisibility(View.GONE);
                Unfavorite_Card.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void showWait() {
        activityDetailLoadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_OUT)
                .duration(300)
                .createFor(activityDetailLoadingLayout)
                .start();
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getPopularListSuccess(MoviesListDao moviesListDao) {

    }

    @Override
    public void getTopRatedListSuccess(MoviesListDao moviesListDao) {

    }

    @Override
    public void getNowPlayingListSuccess(MoviesListDao moviesListDao) {

    }

    @Override
    public void getMoviesDetailSuccess(DetailApiDao detailApiDao) {

    }

    @Override
    public void getTrailersSuccess(final TrailersListDao trailersListDao) {
        activityDetailPlayTrailerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trailersListDao != null) {
                    Intent trailer = new Intent(DetailActivity.this, YoutubeActivity.class);
                    trailer.putExtra("videoKey", trailersListDao.getResults().get(0).getKey());
                    startActivity(trailer);
                } else {
                    Toast.makeText(DetailActivity.this, "No Trailers available for this movie", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void getSimilarSuccess(SimilarListDao similarListDao) {

        /** if (similarListDao.getResults().size() == 0) {
         activityDetailSimilarLinearLayout.setVisibility(View.GONE);
         }

         activityDetailContentRelativeLayout.setVisibility(View.GONE);
         dialog.show();

         MoviesSimilarAdapter adapter = new MoviesSimilarAdapter(getApplicationContext(), similarListDao, utils, gliding,
         new MoviesSimilarAdapter.OnItemClickListener() {
        @Override public void onClick(SimilarApiDao item) {
        String genres = utils.getGenres(item.getGenre_ids());
        ParcelableMovies movies = parcefy.fillSimilarParcelable(item, genres);
        Intent detail = new Intent(DetailActivity.this, DetailActivity.class);
        detail.putExtra("parcelableMovies", movies);
        detail.putExtra("id", item.getId());
        startActivity(detail);
        }
        });

         utils.initRecyclerView(DetailActivity.this, activityDetailRvSimilar, adapter);
         dialog.dismiss();
         activityDetailContentRelativeLayout.setVisibility(View.VISIBLE); **/
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
