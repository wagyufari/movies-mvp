package com.nacoda.moviesmvpdagger2rxjava.main.adapters;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.main.DetailActivity;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;
import com.nacoda.moviesmvpdagger2rxjava.utils.Gliding;
import com.nacoda.moviesmvpdagger2rxjava.utils.Parcefy;
import com.nacoda.moviesmvpdagger2rxjava.utils.Utils;

import static com.nacoda.moviesmvpdagger2rxjava.Config.IMAGE_URL;

public class MoviesSliderAdapter extends PagerAdapter {

    private MoviesListDao moviesListDao;
    private LayoutInflater inflater;
    private Activity activity;
    private Parcefy parcefy;
    private Utils utils;
    Gliding gliding;

    public MoviesSliderAdapter(Activity activity, MoviesListDao moviesListDao, Parcefy parcefy, Utils utils, Gliding gliding) {
        this.activity = activity;
        this.moviesListDao = moviesListDao;
        inflater = LayoutInflater.from(activity);
        this.parcefy = parcefy;
        this.utils = utils;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View mView = inflater.inflate(R.layout.list_movies_slider_header, view, false);

        final ImageView moviesMainBackdropImageView = (ImageView) mView.findViewById(R.id.movies_main_backdrop_image_view);
        ImageView moviesMainPosterImageView = (ImageView) mView.findViewById(R.id.movies_main_poster_image_view);
        TextView moviesMainTitleTextView = (TextView) mView.findViewById(R.id.movies_main_title_text_view);
        TextView moviesMainGenreTextView = (TextView) mView.findViewById(R.id.movies_main_genre_text_view);
        TextView moviesReleaseDateTextView = (TextView) mView.findViewById(R.id.movies_main_release_date_text_view);
        RatingBar moviesMainVoteAverageRatingBar = (RatingBar) mView.findViewById(R.id.movies_main_vote_average_rating_bar);


        Glide.with(activity).load(IMAGE_URL + moviesListDao.getResults().get(position).getBackdrop_path()).asBitmap().into(new SimpleTarget<Bitmap>(400, 400) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    moviesMainBackdropImageView.setBackground(drawable);
                }
            }
        });

        Glide.with(activity).load(IMAGE_URL + moviesListDao.getResults().get(position).getPoster_path())
                .crossFade()
                .centerCrop()
                .into(moviesMainPosterImageView);

        moviesMainTitleTextView.setText(moviesListDao.getResults().get(position).getTitle());
        moviesMainGenreTextView.setText(utils.getGenres(moviesListDao.getResults().get(position).getGenre_ids()));
        moviesReleaseDateTextView.setText(moviesListDao.getResults().get(position).getRelease_date());
        moviesMainVoteAverageRatingBar.setRating(moviesListDao.getResults().get(position).getVote_average() / 2);

        moviesMainBackdropImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genres = utils.getGenres(moviesListDao.getResults().get(position).getGenre_ids());

                ParcelableMovies movies = parcefy.fillParcelableList(moviesListDao, genres, position);

                Intent detail = new Intent(activity, DetailActivity.class);
                detail.putExtra("parcelableMovies", movies);
                detail.putExtra("id", moviesListDao.getResults().get(position).getId());
                activity.startActivity(detail);
                activity.overridePendingTransition(R.anim.slide_up, R.anim.no_change);
            }
        });

        view.addView(mView, 0);
        return mView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}