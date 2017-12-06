package com.nacoda.moviesmvpdagger2rxjava.mvp.favorites;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.adapters.FavoritesAdapter;
import com.nacoda.moviesmvpdagger2rxjava.mvp.favorites.db.AppDatabase;
import com.nacoda.moviesmvpdagger2rxjava.mvp.favorites.db.FavoritesListViewModel;
import com.nacoda.moviesmvpdagger2rxjava.mvp.favorites.db.FavoritesModel;
import com.nacoda.moviesmvpdagger2rxjava.mvp.detail.DetailActivity;
import com.nacoda.moviesmvpdagger2rxjava.models.ParcelableMovies;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity {

    @BindView(R.id.activity_favorites_recycler_view)
    RecyclerView activityFavoritesRecyclerView;
    private FavoritesListViewModel viewModel;
    private FavoritesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        adapter = initAdapter();
        initRecyclerView();

        viewModel = ViewModelProviders.of(this).get(FavoritesListViewModel.class);

        viewModel.getFavoritesList().observe(FavoritesActivity.this, new Observer<List<FavoritesModel>>() {
            @Override
            public void onChanged(@Nullable List<FavoritesModel> favorites) {
                adapter.addItems(favorites);
            }
        });

    }


    private void initRecyclerView() {
        activityFavoritesRecyclerView.setLayoutManager(initLayoutManager());
        activityFavoritesRecyclerView.setAdapter(adapter);
    }   

    private RecyclerView.LayoutManager initLayoutManager() {
        return new LinearLayoutManager(this);
    }

    private FavoritesAdapter initAdapter() {
        return new FavoritesAdapter(new ArrayList<FavoritesModel>(),

                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        FavoritesModel favoritesModel = (FavoritesModel) view.getTag();
                        viewModel.deleteFavorites(favoritesModel);
                        return true;
                    }
                },
                new FavoritesAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(FavoritesModel item) {
                        ParcelableMovies movies = fillFavoritesParcelable(item);
                        Intent detail = new Intent(FavoritesActivity.this, DetailActivity.class);
                        detail.putExtra("parcelableMovies", movies);
                        detail.putExtra("id", item.getMovieId());
                        startActivity(detail);
                    }
                },

                FavoritesActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppDatabase.destroyInstance();
    }

    public ParcelableMovies fillFavoritesParcelable(FavoritesModel item) {
        ParcelableMovies parcelableMovies = new ParcelableMovies(
                item.getPoster_path(),
                item.getBackdrop_path(),
                item.getTitle(),
                item.getRelease_date(),
                item.getMovieId(),
                item.getOverview(),
                item.getGenres(),
                item.getVote_average(),
                item.getVote_count()
        );
        return parcelableMovies;
    }
}
