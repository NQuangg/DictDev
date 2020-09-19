package com.example.myapplication.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.db.model.ContentWord;
import com.example.myapplication.db.model.TitleWord;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContentWordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(ArrayList<ContentWord> contentWords);

    @Query("DELETE FROM content_word_table")
    void deleteAll();

    @Query("SELECT * from content_word_table WHERE name = :nameWord")
    LiveData<List<ContentWord>> getContentWords(String nameWord);
}
