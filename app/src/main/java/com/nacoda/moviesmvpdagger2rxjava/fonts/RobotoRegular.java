package com.nacoda.moviesmvpdagger2rxjava.fonts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Mayburger on 9/26/17.
 */


public class RobotoRegular extends android.support.v7.widget.AppCompatTextView {


    public RobotoRegular(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_regular.ttf");
        this.setTypeface(face);
    }

    public RobotoRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_regular.ttf");
        this.setTypeface(face);
    }

    public RobotoRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_regular");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}