package com.nacoda.moviesmvpdagger2rxjava.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.nacoda.moviesmvpdagger2rxjava.BaseActivity;
import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.main.adapters.TabSliderAdapter;
import com.nacoda.moviesmvpdagger2rxjava.main.fragments.now.NowPlayingFragment;
import com.nacoda.moviesmvpdagger2rxjava.main.fragments.popular.PopularFragment;
import com.nacoda.moviesmvpdagger2rxjava.main.fragments.top.TopFragment;
import com.nacoda.moviesmvpdagger2rxjava.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMoviesActivity extends BaseActivity {

    @BindView(R.id.activity_movies_main_tab_layout)
    TabLayout activityMoviesMainTabLayout;
    @BindView(R.id.activity_movies_main_tab_slider_view_pager)
    ViewPager activityMoviesMainTabSliderViewPager;
    @BindView(R.id.activity_movies_main_toolbar)
    Toolbar activityMoviesMainToolbar;
    @Inject
    Utils utils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
        setContentView(R.layout.activity_movies_main);
        ButterKnife.bind(this);
        setSupportActionBar(activityMoviesMainToolbar);
        onSetupViewPager(activityMoviesMainTabSliderViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_movies_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_movies_main_search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_movies_main_favorites:
                startActivity(new Intent(MainMoviesActivity.this, FavoritesActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method is used to initialize the TabSlider using Viewpager
     *
     * @param viewPager The ViewPager used for the TabSlider
     */
    private void onSetupViewPager(ViewPager viewPager) {
        TabSliderAdapter adapter = new TabSliderAdapter(getSupportFragmentManager());
        adapter.addFragment(new PopularFragment(), "POPULAR");
        adapter.addFragment(new NowPlayingFragment(), "IN THEATER");
        adapter.addFragment(new TopFragment(), "TOP");
        viewPager.setAdapter(adapter);
        activityMoviesMainTabLayout.setTabTextColors(getResources().getColor(R.color.greyIndicator), getResources().getColor(R.color.colorAccent));
        activityMoviesMainTabLayout.setupWithViewPager(activityMoviesMainTabSliderViewPager);
    }


}