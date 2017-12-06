package com.nacoda.moviesmvpdagger2rxjava.mvp.favorites.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

public class AddFavoritesViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public AddFavoritesViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

    }

    public void addFavorites(final FavoritesModel favoritesModel) {
        new addAsyncTask(appDatabase).execute(favoritesModel);
    }

    private static class addAsyncTask extends AsyncTask<FavoritesModel, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final FavoritesModel... params) {
            db.favoritesModel().addToFavorites(params[0]);
            return null;
        }

    }
}
