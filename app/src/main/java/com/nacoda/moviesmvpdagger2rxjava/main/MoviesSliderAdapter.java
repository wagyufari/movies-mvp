package com.nacoda.moviesmvpdagger2rxjava.main;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;

import java.util.ArrayList;
import java.util.Arrays;

public class MoviesSliderAdapter extends PagerAdapter {

    private MoviesListDao moviesListDao;
    private LayoutInflater inflater;
    private Context context;

    public MoviesSliderAdapter(Context context, MoviesListDao moviesListDao) {
        this.context = context;
        this.moviesListDao=moviesListDao;
        inflater = LayoutInflater.from(context);
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
    public Object instantiateItem(ViewGroup view, int position) {
        View mView = inflater.inflate(R.layout.list_movies_slider_header, view, false);

        String IMAGE_URL = "http://image.tmdb.org/t/p/w780/";

        ImageView moviesMainBackdropImageView = (ImageView) mView.findViewById(R.id.movies_main_backdrop_image_view);
        ImageView moviesMainPosterImageView = (ImageView) mView.findViewById(R.id.movies_main_poster_image_view);
        TextView moviesMainTitleTextView = (TextView) mView.findViewById(R.id.movies_main_title_text_view);
        TextView moviesMainReleaseDateTextView = (TextView) mView.findViewById(R.id.movies_main_release_date_text_view);
        TextView moviesMainGenreTextView = (TextView) mView.findViewById(R.id.movies_main_release_date_text_view);
        RatingBar moviesMainVoteAverageRatingBar = (RatingBar) mView.findViewById(R.id.movies_main_vote_average_rating_bar);


        Glide.with(context)
                .load(IMAGE_URL + moviesListDao.getResults().get(position).getBackdrop_path())
                .crossFade()
                .override(2200, 2000)
                .centerCrop()
                .into(moviesMainBackdropImageView);

        Glide.with(context).load(IMAGE_URL + moviesListDao.getResults().get(position).getPoster_path())
                .crossFade()
                .centerCrop()
                .into(moviesMainPosterImageView);

        moviesMainTitleTextView.setText(moviesListDao.getResults().get(position).getTitle());
        moviesMainReleaseDateTextView.setText(moviesListDao.getResults().get(position).getRelease_date());
        moviesMainGenreTextView.setText(getGenres(moviesListDao.getResults().get(position).getGenre_ids()));
        moviesMainVoteAverageRatingBar.setRating(moviesListDao.getResults().get(position).getVote_average() / 2);

        view.addView(mView, 0);
        return mView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
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