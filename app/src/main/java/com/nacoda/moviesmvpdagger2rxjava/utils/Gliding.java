package com.nacoda.moviesmvpdagger2rxjava.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by Mayburger on 10/5/17.
 */

public class Gliding {

    @BindingAdapter({"bind:poster"})
    public static void loadPoster(final ImageView view, String url) {
        Glide.with(view.getContext()).load(url)
                .crossFade()
                .centerCrop()
                .into(view);
    }

    @BindingAdapter({"bind:backdrop"})
    public static void loadBackdrop(final ImageView view, String url) {
        Glide.with(view.getContext()).load(url).asBitmap().into(new SimpleTarget<Bitmap>(400, 400) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(drawable);
                }
            }
        });
    }

    public void GlideBackdrop(Context context, String url, final View view) {
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

    public void GlidePoster(Context context, String url, ImageView view){
        Glide.with(context).load(url)
                .crossFade()
                .centerCrop()
                .into(view);
    }

}
