package com.example.myapplication.db.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myapplication.db.model.Word;

@Entity(tableName = "searched_word_table")
public class SearchedWord extends Word {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String word;

    public SearchedWord(@NonNull String word) {
        this.word = word;
    }

    @Override
    @NonNull
    public String getWord() {
        return word;
    }
}
