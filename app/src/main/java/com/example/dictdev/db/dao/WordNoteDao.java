package com.example.dictdev.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dictdev.db.model.entity.WordNote;

@Dao
public interface WordNoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WordNote wordNote);

    @Query("SELECT * FROM word_note_table WHERE word =:noteWord")
    LiveData<WordNote> getNote(String noteWord);

}
