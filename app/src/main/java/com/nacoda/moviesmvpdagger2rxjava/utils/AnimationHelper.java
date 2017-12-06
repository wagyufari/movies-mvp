package com.nacoda.moviesmvpdagger2rxjava.utils;

import android.view.View;
import android.view.animation.Animation;

import com.nacoda.moviesmvpdagger2rxjava.R;

/**
 * Created by Mayburger on 11/5/17.
 */

public class AnimationHelper {
    public static void onSlideAnim(View view) {
        Animation anim = android.view.animation.AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_down);
        view.startAnimation(anim);
    }
}
