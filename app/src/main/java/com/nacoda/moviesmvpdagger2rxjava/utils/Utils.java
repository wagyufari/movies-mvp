package com.nacoda.moviesmvpdagger2rxjava.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.appolica.flubber.Flubber;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.Config;
import com.nacoda.moviesmvpdagger2rxjava.models.Detail;
import com.nacoda.moviesmvpdagger2rxjava.models.DetailApiDao;
import com.nacoda.moviesmvpdagger2rxjava.models.Movies;
import com.nacoda.moviesmvpdagger2rxjava.models.MoviesListDao;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;

import java.util.Arrays;

/**
 * Created by Mayburger on 10/5/17.
 */

public class Utils {

    public void bindDetails(Detail detail, DetailApiDao detailApiDao){
        detail.setBackdrop_path(Config.IMAGE_URL + detailApiDao.getBackdrop_path());
        detail.setBudget(detailApiDao.getBudget());
        detail.setHomepage(detailApiDao.getHomepage());
        detail.setRuntime(detailApiDao.getRuntime() + " Minutes");
        detail.setTagline(detailApiDao.getTagline());

    }

    public void bindMoviesParcelable(Movies movies, ParcelableMovies parcelableMovies){
        movies.setPoster_path(Config.IMAGE_URL + parcelableMovies.getPoster_path());
        movies.setBackdrop_path(Config.IMAGE_URL + parcelableMovies.getBackdrop_path());
        movies.setTitle(parcelableMovies.getTitle());
        movies.setGenre_ids(parcelableMovies.getGenres());
        movies.setId(parcelableMovies.getId());
        movies.setOverview(parcelableMovies.getOverview());
        movies.setRelease_date(parcelableMovies.getRelease_date());
        movies.setVote_average(parcelableMovies.getVote_average() / 2);
        movies.setVote_count(parcelableMovies.getVote_count());
    }

    public void bindMovies(Movies movies, MoviesListDao moviesListDao, int position){
        String genres = getGenres(moviesListDao.getResults().get(position).getGenre_ids());
        movies.setPoster_path(Config.IMAGE_URL + moviesListDao.getResults().get(position).getPoster_path());
        movies.setBackdrop_path(Config.IMAGE_URL + moviesListDao.getResults().get(position).getBackdrop_path());
        movies.setTitle(moviesListDao.getResults().get(position).getTitle());
        movies.setGenre_ids(genres);
        movies.setId(moviesListDao.getResults().get(position).getId());
        movies.setOverview(moviesListDao.getResults().get(position).getOverview());
        movies.setRelease_date(moviesListDao.getResults().get(position).getRelease_date());
        movies.setVote_average(moviesListDao.getResults().get(position).getVote_average() / 2);
        movies.setVote_count(moviesListDao.getResults().get(position).getVote_count());
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

    public void initFadeinFlubber(View view) {
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_IN)
                .repeatCount(0)
                .duration(300)
                .createFor(view)
                .start();
    }

    public void arrowHelper(int page, View activityMoviesArrowRight, View activityMoviesArrowLeft) {
        if (page >= 1) {
            activityMoviesArrowRight.setVisibility(View.VISIBLE);
        }
        if (page > 1) {
            activityMoviesArrowLeft.setVisibility(View.VISIBLE);
        }
    }

    public void setProgressbar(Dialog dialog){
        /** Set the progressbar color to white **/
        ProgressBar progressBar = dialog.findViewById(R.id.pbLoad);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
        }
        /** Set the progressbar color to white **/
    }

    public void initRecyclerView(Context context, RecyclerView recyclerView, RecyclerView.Adapter adapter){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}
