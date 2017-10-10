package com.nacoda.moviesmvpdagger2rxjava.main;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.nacoda.moviesmvpdagger2rxjava.R;
import com.nacoda.moviesmvpdagger2rxjava.main.adapters.FavoritesAdapter;
import com.nacoda.moviesmvpdagger2rxjava.main.db.AddFavoritesViewModel;
import com.nacoda.moviesmvpdagger2rxjava.main.db.AppDatabase;
import com.nacoda.moviesmvpdagger2rxjava.main.db.FavoritesListViewModel;
import com.nacoda.moviesmvpdagger2rxjava.main.db.FavoritesModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FavoritesActivity extends LifecycleActivity implements View.OnLongClickListener {


    @InjectView(R.id.activity_favorites_recycler_view)
    RecyclerView activityFavoritesRecyclerView;
    private FavoritesListViewModel viewModel;
    private FavoritesAdapter adapter;
    private AddFavoritesViewModel addViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.inject(this);
        adapter = initAdapter();
        initRecyclerView();

        viewModel = ViewModelProviders.of(this).get(FavoritesListViewModel.class);
        addViewModel = ViewModelProviders.of(this).get(AddFavoritesViewModel.class);

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
        return new GridLayoutManager(this, 2);
    }

    private FavoritesAdapter initAdapter() {
        return new FavoritesAdapter(new ArrayList<FavoritesModel>(), this, FavoritesActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppDatabase.destroyInstance();
    }

    @Override
    public boolean onLongClick(View v) {
        FavoritesModel favoritesModel = (FavoritesModel) v.getTag();
        viewModel.deleteFavorites(favoritesModel);
        return true;
    }
}
