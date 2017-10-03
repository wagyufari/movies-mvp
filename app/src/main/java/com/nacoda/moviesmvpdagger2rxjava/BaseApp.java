package com.nacoda.moviesmvpdagger2rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nacoda.moviesmvpdagger2rxjava.deps.DaggerDeps;
import com.nacoda.moviesmvpdagger2rxjava.deps.Deps;
import com.nacoda.moviesmvpdagger2rxjava.networking.NetworkModule;

import java.io.File;

/**
 * Created by Mayburger on 10/3/17.
 */

public class BaseApp  extends AppCompatActivity {
    Deps deps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();

    }

    public Deps getDeps() {
        return deps;
    }
}