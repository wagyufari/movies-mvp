package com.nacoda.moviesmvpdagger2rxjava.mvp.favorites.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;


import java.util.List;


public class FavoritesListViewModel extends AndroidViewModel {

    private final LiveData<List<FavoritesModel>> favoritesList;

    private AppDatabase appDatabase;

    public FavoritesListViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        favoritesList = appDatabase.favoritesModel().getAllFavorites();
    }


    public LiveData<List<FavoritesModel>> getFavoritesList() {
        return favoritesList;
    }

    public void deleteFavorites(FavoritesModel favoritesModel) {
        new deleteAsyncTask(appDatabase).execute(favoritesModel);
    }

    private static class deleteAsyncTask extends AsyncTask<FavoritesModel, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final FavoritesModel... params) {
            db.favoritesModel().deleteFavorites(params[0]);
            return null;
        }

    }

}
