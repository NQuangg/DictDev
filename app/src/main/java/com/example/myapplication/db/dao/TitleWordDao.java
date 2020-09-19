package com.example.myapplication.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.db.model.TitleWord;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TitleWordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(ArrayList<TitleWord> titleWords);

    @Query("DELETE FROM title_word_table")
    void deleteAll();

    @Query("SELECT * FROM title_word_table WHERE name = :nameWord")
    LiveData<TitleWord> getTitleWord(String nameWord);

    @Query("SELECT * from title_word_table")
    LiveData<List<TitleWord>> getAllTitleWords();
}
