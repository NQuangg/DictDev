package com.example.myapplication.db.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "title_word_table")
public class TitleWord {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "pronounce")
    private String pronounce;

    public TitleWord(@NonNull String name, @NonNull String pronounce) {
        this.name = name;
        this.pronounce = pronounce;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getPronounce() {
        return pronounce;
    }
}
