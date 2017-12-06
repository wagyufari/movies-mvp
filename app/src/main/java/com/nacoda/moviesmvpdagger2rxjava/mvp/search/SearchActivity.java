package com.nacoda.moviesmvpdagger2rxjava.mvp.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.nacoda.moviesmvpdagger2rxjava.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {


    @BindView(R.id.activity_search_toolbar)
    Toolbar activitySearchToolbar;
    @BindView(R.id.activity_search_edit_text)
    EditText activitySearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(activitySearchToolbar);
        onSetupToolbar(activitySearchToolbar);
        initSearch();
    }

    private void initSearch() {
        final Intent search = new Intent(this, SearchDetailActivity.class);
        activitySearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_GO)) {
                    search.putExtra("query", activitySearchEditText.getText().toString());
                    startActivity(search);
                    activitySearchEditText.setText("");
                }
                return false;
            }
        });
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

        }
    }
}
