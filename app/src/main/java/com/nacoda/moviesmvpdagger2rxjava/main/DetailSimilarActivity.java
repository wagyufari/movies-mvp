package com.nacoda.moviesmvpdagger2rxjava.main;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.nacoda.moviesmvpdagger2rxjava.BaseApp;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.databinding.ActivityDetailBinding;
import com.nacoda.moviesmvpdagger2rxjava.fonts.RobotoBold;
import com.nacoda.moviesmvpdagger2rxjava.fonts.RobotoLight;
import com.nacoda.moviesmvpdagger2rxjava.main.adapters.MoviesSimilarAdapter;
import com.nacoda.moviesmvpdagger2rxjava.main.adapters.TrailersAdapter;
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

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.nacoda.moviesmvpdagger2rxjava.Config.IMAGE_URL;

public class DetailSimilarActivity extends BaseApp implements MoviesView {

    @Inject Service service;
    @Inject Gliding gliding;
    @Inject Parcefy parcefy;
    @Inject Utils utils;
    @Inject
    Detail detail;
    @Inject
    Movies movies;
    @Inject
    Settings settings;

    ParcelableMovies parcelableMovies;
    Dialog dialog;

    @InjectView(R.id.activity_detail_content_relative_layout)
    RelativeLayout activityDetailContentRelativeLayout;
    @InjectView(R.id.activity_detail_rv_trailers)
    RecyclerView activityDetailRvTrailers;
    @InjectView(R.id.activity_detail_rv_similar)
    RecyclerView activityDetailRvSimilar;
    ActivityDetailBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);
        getApplicationComponent().inject(this);
        initDialog();
        getParcelable();
        initRetrofit();


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

        /** Set the progressbar color to white **/
        ProgressBar progressBar = dialog.findViewById(R.id.pbLoad);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
        }
        /** Set the progressbar color to white **/
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
    public void getTrailersSuccess(TrailersListDao trailersListDao) {

        activityDetailContentRelativeLayout.setVisibility(View.GONE);
        dialog.show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        TrailersAdapter adapter = new TrailersAdapter(getApplicationContext(), trailersListDao, gliding,
                new TrailersAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(TrailersApiDao item) {
                        Intent youtube = new Intent(DetailSimilarActivity.this, YoutubeActivity.class);
                        youtube.putExtra("videoKey", item.getKey());
                        startActivity(youtube);
                    }
                });
        activityDetailRvTrailers.setAdapter(adapter);
        activityDetailRvTrailers.setLayoutManager(layoutManager);

        dialog.dismiss();

    }

    @Override
    public void getSimilarSuccess(SimilarListDao similarListDao) {

        activityDetailContentRelativeLayout.setVisibility(View.GONE);
        dialog.show();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        MoviesSimilarAdapter adapter = new MoviesSimilarAdapter(getApplicationContext(), similarListDao,utils,gliding,
                new MoviesSimilarAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(SimilarApiDao item) {
                        String genres = utils.getGenres(item.getGenre_ids());
                        ParcelableMovies movies = parcefy.fillSimilarParcelable(item, genres);

                        Intent detail = new Intent(DetailSimilarActivity.this, DetailActivity.class);
                        detail.putExtra("parcelableMovies", movies);
                        detail.putExtra("id", item.getId());
                        startActivity(detail);
                        overridePendingTransition(R.anim.slide_up, R.anim.no_change);
                        finish();
                    }
                });
        activityDetailRvSimilar.setAdapter(adapter);
        activityDetailRvSimilar.setLayoutManager(layoutManager);

        dialog.dismiss();
        activityDetailContentRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
