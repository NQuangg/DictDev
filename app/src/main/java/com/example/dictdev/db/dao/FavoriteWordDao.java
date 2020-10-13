package com.example.dictdev.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.dictdev.db.model.entity.FavoriteWord;

import java.util.List;

@Dao
public interface FavoriteWordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FavoriteWord favoriteWord);

    @Delete
    void delete(FavoriteWord favoriteWord);

    @Query("SELECT * FROM favorite_word_table WHERE word = :nameWord")
    LiveData<FavoriteWord> getFavoriteWord(String nameWord);

    @Query("SELECT * FROM favorite_word_table")
    LiveData<List<FavoriteWord>> getAllFavoriteWords();
}
