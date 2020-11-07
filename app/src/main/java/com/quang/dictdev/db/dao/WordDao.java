package com.quang.dictdev.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.quang.dictdev.db.model.entity.Word;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ArrayList<Word> words);

    @Query("DELETE FROM word_table")
    void deleteAll();

    // get word name
    @Query("SELECT name FROM word_table")
    LiveData<List<String>> getAllWordNames();

    // get word
    @Query("SELECT * FROM word_table WHERE name =:wordName")
    LiveData<Word> getWord(String wordName);

    // update searchedWord
    @Query("UPDATE word_table SET is_searched =:isSearched WHERE name =:wordName")
    void updateSearchedWord(String wordName, String isSearched);

    // get all searchedWords
    @Query("SELECT name FROM word_table WHERE is_searched = 1")
    LiveData<List<String>> getAllSearchedWordNames();

    // update favoriteWord
    @Query("UPDATE word_table SET is_favorite =:isFavorite WHERE name =:wordName")
    void updateFavoriteWord(String wordName, String isFavorite);

    // get favorite
    @Query("SELECT is_favorite FROM word_table WHERE name =:nameWord")
    LiveData<String> getFavorite(String nameWord);

    // get all favoriteWords
    @Query("SELECT name FROM word_table WHERE is_favorite = 1")
    LiveData<List<String>> getAllFavoriteWordNames();

    // set note
    @Query("UPDATE word_table SET note =:note WHERE name =:nameWord")
    void setNote(String nameWord, String note);

    // get note
    @Query("SELECT note FROM word_table WHERE name =:nameWord")
    LiveData<String> getNote(String nameWord);
}
