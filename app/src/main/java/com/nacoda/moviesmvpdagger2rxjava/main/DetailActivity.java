package com.nacoda.moviesmvpdagger2rxjava.main;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nacoda.moviesmvpdagger2rxjava.BaseApp;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.databinding.ActivityDetailBinding;
import com.nacoda.moviesmvpdagger2rxjava.main.adapters.MoviesSimilarAdapter;
import com.nacoda.moviesmvpdagger2rxjava.main.adapters.TrailersAdapter;
import com.nacoda.moviesmvpdagger2rxjava.main.db.AddFavoritesViewModel;
import com.nacoda.moviesmvpdagger2rxjava.main.db.FavoritesListViewModel;
import com.nacoda.moviesmvpdagger2rxjava.main.db.FavoritesModel;
import com.nacoda.moviesmvpdagger2rxjava.models.Detail;
import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.Movies;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;
import com.nacoda.moviesmvpdagger2rxjava.models.Settings;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.SimilarListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.TrailersListDao;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesPresenter;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesView;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;
import com.nacoda.moviesmvpdagger2rxjava.utils.Gliding;
import com.nacoda.moviesmvpdagger2rxjava.utils.Parcefy;
import com.nacoda.moviesmvpdagger2rxjava.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DetailActivity extends BaseApp implements MoviesView {

    @Inject
    Service service;
    @Inject
    Gliding gliding;
    @Inject
    Parcefy parcefy;
    @Inject
    Utils utils;
    @Inject
    Detail detail;
    @Inject
    Movies movies;
    @Inject
    Settings settings;

    ParcelableMovies parcelableMovies;
    Dialog dialog;
    ActivityDetailBinding binding;

    @InjectView(R.id.activity_detail_rv_trailers)
    RecyclerView activityDetailRvTrailers;
    @InjectView(R.id.activity_detail_trailers_linear_layout)
    LinearLayout activityDetailTrailersLinearLayout;
    @InjectView(R.id.activity_detail_rv_similar)
    RecyclerView activityDetailRvSimilar;
    @InjectView(R.id.activity_detail_similar_linear_layout)
    LinearLayout activityDetailSimilarLinearLayout;
    @InjectView(R.id.activity_detail_content_relative_layout)
    RelativeLayout activityDetailContentRelativeLayout;

    @InjectView(R.id.activity_detail_favorite_view)
    View activityDetailFavoriteView;
    @InjectView(R.id.activity_detail_unfavorite_view)
    View activityDetailUnfavoriteView;

    FavoritesListViewModel viewModel;
    AddFavoritesViewModel addViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        ButterKnife.inject(this);
        getApplicationComponent().inject(this);
        initDialog();
        getParcelable();
        initRetrofit();

        viewModel = ViewModelProviders.of(this).get(FavoritesListViewModel.class);
        addViewModel = ViewModelProviders.of(this).get(AddFavoritesViewModel.class);

        viewModel.getFavoritesList().observe(DetailActivity.this, new Observer<List<FavoritesModel>>() {
            @Override
            public void onChanged(@Nullable List<FavoritesModel> favorites) {
                assert favorites != null;
                for (int i = 0; i < favorites.size(); i++) {
                    if (favorites.get(i).getPoster_path().equals(parcelableMovies.getPoster_path())) {
                        final FavoritesModel favoritesModel = (FavoritesModel) favorites.get(i);
                        activityDetailFavoriteView.setVisibility(View.GONE);
                        activityDetailUnfavoriteView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                viewModel.deleteFavorites(favoritesModel);
                                activityDetailUnfavoriteView.setVisibility(View.GONE);
                                activityDetailFavoriteView.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            }
        });

        activityDetailFavoriteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addViewModel.addFavorites(new FavoritesModel(
                        parcelableMovies.getPoster_path(),
                        parcelableMovies.getBackdrop_path(),
                        parcelableMovies.getTitle(),
                        parcelableMovies.getGenres(),
                        parcelableMovies.getId(),
                        parcelableMovies.getOverview(),
                        parcelableMovies.getRelease_date(),
                        parcelableMovies.getVote_average(),
                        parcelableMovies.getVote_count()
                ));
                activityDetailFavoriteView.setVisibility(View.GONE);
                activityDetailUnfavoriteView.setVisibility(View.VISIBLE);
            }
        });

    }

    public void initRetrofit() {
        MoviesPresenter presenter = new MoviesPresenter(service, this);
        String id = getIntent().getStringExtra("id");
        presenter.getMoviesDetail(id);
        presenter.getTrailers(id);
        presenter.getSimilar(id);

    }

    private void getParcelable() {
        parcelableMovies = getIntent().getParcelableExtra("parcelableMovies");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change, R.anim.slide_down);
    }

    @Override
    public void showWait() {
        dialog.show();
    }

    @Override
    public void removeWait() {

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

    private void initDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.loading_dialog);
        utils.setProgressbar(dialog);
    }

    @Override
    public void getMoviesDetailSuccess(DetailApiDao detailApiDao) {

        utils.bindDetails(detail, detailApiDao);
        utils.bindMoviesParcelable(movies, parcelableMovies);

        binding.setMovies(movies);
        binding.setDetail(detail);
        binding.setSettings(settings);
    }

    @Override
    public void getTrailersSuccess(final TrailersListDao trailersListDao) {

        if (trailersListDao.getResults().size() == 0) {
            activityDetailTrailersLinearLayout.setVisibility(View.GONE);
        }
        activityDetailContentRelativeLayout.setVisibility(View.GONE);
        dialog.show();

        TrailersAdapter adapter = new TrailersAdapter(getApplicationContext(), trailersListDao, gliding,
                new TrailersAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(TrailersApiDao item) {
                        Intent youtube = new Intent(DetailActivity.this, YoutubeActivity.class);
                        youtube.putExtra("videoKey", item.getKey());
                        startActivity(youtube);
                    }
                });

        utils.initRecyclerView(DetailActivity.this, activityDetailRvTrailers, adapter);
        dialog.dismiss();

    }

    @Override
    public void getSimilarSuccess(SimilarListDao similarListDao) {

        if (similarListDao.getResults().size() == 0) {
            activityDetailSimilarLinearLayout.setVisibility(View.GONE);
        }

        activityDetailContentRelativeLayout.setVisibility(View.GONE);
        dialog.show();

        MoviesSimilarAdapter adapter = new MoviesSimilarAdapter(getApplicationContext(), similarListDao, utils, gliding,
                new MoviesSimilarAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(SimilarApiDao item) {
                        String genres = utils.getGenres(item.getGenre_ids());
                        ParcelableMovies movies = parcefy.fillSimilarParcelable(item, genres);
                        Intent detail = new Intent(DetailActivity.this, DetailActivity.class);
                        detail.putExtra("parcelableMovies", movies);
                        detail.putExtra("id", item.getId());
                        startActivity(detail);
                        overridePendingTransition(R.anim.slide_up, R.anim.no_change);
                    }
                });

        utils.initRecyclerView(DetailActivity.this, activityDetailRvSimilar, adapter);
        dialog.dismiss();
        activityDetailContentRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
