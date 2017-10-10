package com.nacoda.moviesmvpdagger2rxjava.main.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavoritesModelDao {

    @Query("select * from FavoritesModel")
    LiveData<List<FavoritesModel>> getAllFavorites();

    @Query("select * from FavoritesModel where id = :id")
    FavoritesModel getFavoritesById(String id);

    @Insert(onConflict = REPLACE)
    void addToFavorites(FavoritesModel favoritesModel);

    @Delete
    void deleteFavorites(FavoritesModel favoritesModel);

}
