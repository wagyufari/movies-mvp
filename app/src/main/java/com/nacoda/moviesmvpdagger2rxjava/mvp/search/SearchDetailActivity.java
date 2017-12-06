package com.nacoda.moviesmvpdagger2rxjava.mvp.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.nacoda.moviesmvpdagger2rxjava.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchDetailActivity extends AppCompatActivity {

    @BindView(R.id.activity_search_detail_toolbar)
    Toolbar activitySearchDetailToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        ButterKnife.bind(this);
        setSupportActionBar(activitySearchDetailToolbar);
        onSetupToolbar(activitySearchDetailToolbar);
        Toast.makeText(this, getIntent().getStringExtra("query"), Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is overrided so that when the backbutton is pressed, it will navigate the user to the previous activity
     **/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * This method is used to make the title disappear and add the back button to the appbar
     *
     * @param toolbar The toolbar used for the backbutton
     */
    public void onSetupToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_accent);
            getSupportActionBar().setTitle(getIntent().getStringExtra("query"));

        }
    }
}
