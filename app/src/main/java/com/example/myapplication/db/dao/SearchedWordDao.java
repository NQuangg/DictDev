package com.example.myapplication.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.db.model.SearchedWord;

import java.util.List;

@Dao
public interface SearchedWordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SearchedWord searchedWord);

    @Query("SELECT * FROM searched_word_table")
    LiveData<List<SearchedWord>> getAllSearchedWords();
}
