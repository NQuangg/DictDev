package com.example.myapplication.db.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "searched_word_table")
public class SearchedWord {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String word;

    public SearchedWord(@NonNull String word) {
        this.word = word;
    }

    @NonNull
    public String getWord() {
        return word;
    }
}
