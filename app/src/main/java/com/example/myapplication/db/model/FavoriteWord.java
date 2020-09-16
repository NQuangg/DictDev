package com.example.myapplication.db.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_word_table")
public class FavoriteWord extends Word{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String word;

    public FavoriteWord(@NonNull String word) {
        this.word = word;
    }

    @Override
    @NonNull
    public String getWord() {
        return word;
    }
}
