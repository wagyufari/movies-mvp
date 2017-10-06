package com.nacoda.moviesmvpdagger2rxjava.fonts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Mayburger on 9/26/17.
 */


public class RobotoLightEdit extends android.support.v7.widget.AppCompatEditText {


    public RobotoLightEdit(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf");
        this.setTypeface(face);
    }

    public RobotoLightEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf");
        this.setTypeface(face);
    }

    public RobotoLightEdit(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}