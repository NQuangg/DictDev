package com.example.dictdev.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.dictdev.db.model.entity.SearchedWord;

import java.util.List;

@Dao
public interface SearchedWordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SearchedWord searchedWord);

    @Delete
    void delete(SearchedWord searchedWord);

    @Query("SELECT * FROM searched_word_table")
    LiveData<List<SearchedWord>> getAllSearchedWords();
}
