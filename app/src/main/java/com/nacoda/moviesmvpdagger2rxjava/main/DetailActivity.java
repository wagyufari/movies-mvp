package com.nacoda.moviesmvpdagger2rxjava.main;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nacoda.moviesmvpdagger2rxjava.BaseApp;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.fonts.RobotoBold;
import com.nacoda.moviesmvpdagger2rxjava.fonts.RobotoLight;
import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesPresenter;
import com.nacoda.moviesmvpdagger2rxjava.mvp.MoviesView;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.nacoda.moviesmvpdagger2rxjava.URL.IMAGE_URL;

public class DetailActivity extends BaseApp implements MoviesView {

    @Inject
    public Service service;

    ParcelableMovies parcelableMovies;

    Dialog dialog;
    @InjectView(R.id.activity_detail_backdrop_image_view)
    ImageView activityDetailBackdropImageView;
    @InjectView(R.id.activity_detail_title_text_view)
    RobotoBold activityDetailTitleTextView;
    @InjectView(R.id.activity_detail_tagline_text_view)
    RobotoLight activityDetailTaglineTextView;
    @InjectView(R.id.activity_detail_vote_average_rating_bar)
    RatingBar activityDetailVoteAverageRatingBar;
    @InjectView(R.id.activity_detail_runtime_icon_image_view)
    ImageView activityDetailRuntimeIconImageView;
    @InjectView(R.id.activity_detail_runtime_text_view)
    RobotoLight activityDetailRuntimeTextView;
    @InjectView(R.id.activity_runtime_linear_layout)
    LinearLayout activityRuntimeLinearLayout;
    @InjectView(R.id.activity_detail_overview_text_view)
    RobotoLight activityDetailOverviewTextView;
    @InjectView(R.id.activity_detail_content_relative_layout)
    RelativeLayout activityDetailContentRelativeLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);
        getDeps().inject(this);
        initDialog();
        getParcelable();
        initRetrofit();
    }

    public void initRetrofit() {
        final MoviesPresenter presenter = new MoviesPresenter(service, this);
        presenter.getMoviesDetail(getIntent().getStringExtra("id"));

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
        dialog.dismiss();
        activityDetailContentRelativeLayout.setVisibility(View.VISIBLE);
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

        activityDetailTitleTextView.setText(parcelableMovies.getTitle());
        activityDetailRuntimeTextView.setText(detailApiDao.getRuntime() + " Minutes");
        activityDetailOverviewTextView.setText(parcelableMovies.getOverview());

        if (detailApiDao.getTagline() != null) {
            activityDetailTaglineTextView.setVisibility(View.VISIBLE);
            activityDetailTaglineTextView.setText(detailApiDao.getTagline());
        }

        activityDetailVoteAverageRatingBar.setRating(parcelableMovies.getVote_average() / 2);

        Glide.with(this).load(IMAGE_URL + detailApiDao.getBackdrop_path()).asBitmap().into(new SimpleTarget<Bitmap>(400, 400) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    activityDetailBackdropImageView.setBackground(drawable);
                }
            }
        });
    }
}
