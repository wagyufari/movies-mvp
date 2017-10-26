package com.nacoda.moviesmvpdagger2rxjava;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nacoda.moviesmvpdagger2rxjava.dagger.ApplicationComponent;
import com.nacoda.moviesmvpdagger2rxjava.dagger.DaggerApplicationComponent;
import com.nacoda.moviesmvpdagger2rxjava.dagger.modules.NetworkModule;
import com.nacoda.moviesmvpdagger2rxjava.dagger.modules.UtilityModule;

import java.io.File;

/**
 * Created by Mayburger on 10/3/17.
 */

public class BaseActivity extends AppCompatActivity {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        applicationComponent = DaggerApplicationComponent.builder()
                .utilityModule(new UtilityModule())
                .networkModule(new NetworkModule(cacheFile))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}