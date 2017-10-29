package com.nacoda.moviesmvpdagger2rxjava.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appolica.flubber.Flubber;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.jaouan.revealator.Revealator;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.Constants;
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

    public void yoyo(View view, Techniques techniques, int duration){
        YoYo.with(techniques)
                .duration(duration)
                .playOn(view);
    }

    public void flubber(View view, int duration, Flubber.AnimationProvider animationProvider) {
        Flubber.with()
                .animation(animationProvider)
                .duration(duration)
                .createFor(view)
                .start();
    }

    public void unreveal(View viewToUnreveal, int duration) {
        Revealator.unreveal(viewToUnreveal)
                .withUnrevealDuration(duration)
                .withCurvedTranslation()
                .start();
    }

    public void revealWithChild(View viewYouWantToReveal, int duration, int childDuration) {
        Revealator.reveal(viewYouWantToReveal)
                .withRevealDuration(duration)
                .withChildAnimationDuration(childDuration)
                .withCurvedTranslation()
                .withChildsAnimation()
                .start();
    }

    public void revealWithoutChild(View viewYouWantToReveal, int duration){
        Revealator.reveal(viewYouWantToReveal)
                .withRevealDuration(duration)
                .withCurvedTranslation()
                .start();
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

    public void snackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public void setProgressbarDialog(Dialog dialog) {
        /** Set the progressbar color to white **/
        ProgressBar progressBar = dialog.findViewById(R.id.pbLoad);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
        }
        /** Set the progressbar color to white **/
    }
}
