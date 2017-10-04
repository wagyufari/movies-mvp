package com.nacoda.moviesmvpdagger2rxjava.main;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appolica.flubber.Flubber;
import com.nacoda.moviesmvpdagger2rxjava.BaseApp;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;
import com.nacoda.moviesmvpdagger2rxjava.networking.Service;

import javax.inject.Inject;

public class DetailActivity extends BaseApp {

    @Inject
    public Service service;

    ParcelableMovies parcelableMovies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getDeps().inject(this);
        getParcelable();
//        initFlubber();
    }

//    public void initFlubber() {
//        Flubber.with()
//                .animation(Flubber.AnimationPreset.FADE_IN)
//                .repeatCount(0)
//                .duration(500)
//                .createFor(findViewById(R.id.flubberLayout))
//                .start();
//    }

    private void getParcelable() {
        parcelableMovies = getIntent().getParcelableExtra("parcelableMovies");
    }
}
