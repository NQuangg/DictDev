package com.example.dictdev.db.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_note_table")
public class WordNote {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String word;

    @ColumnInfo(name = "note")
    private String note;

    public WordNote(@NonNull String word, String note) {
        this.word = word;
        this.note = note;
    }

    @NonNull
    public String getWord() {
        return word;
    }

    public String getNote() {
        return note;
    }

}
