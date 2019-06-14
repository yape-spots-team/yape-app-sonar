package com.software.yapespots.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.software.yapespots.model.local.FavoritePlace;

import java.util.List;

@Dao
public interface FavoritePlaceDao {
   /* @Query("SELECT * FROM favoriteplace")
    List<FavoritePlace> getAll();

    @Query("SELECT * FROM favoriteplace WHERE id = :favoritePlaceId")
    List<FavoritePlace> loadById(String favoritePlaceId);

    @Insert
    void insertAll(FavoritePlace... favoritePlaces);

    @Delete
    void delete(FavoritePlace favoritePlace);*/
}
