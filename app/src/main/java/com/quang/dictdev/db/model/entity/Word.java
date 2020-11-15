package com.quang.dictdev.db.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.quang.dictdev.db.model.WordContent;

import java.util.ArrayList;

@Entity(tableName = "word_table")
public class Word {
    @PrimaryKey
    @NonNull
    private String name;

    @NonNull
    private String pronounce;

    @NonNull
    private String isSearched;

    @NonNull
    private String isFavorite;

    @NonNull
    private String note;

    @NonNull
    private ArrayList<WordContent> contents;

    public Word(@NonNull String name, @NonNull String pronounce, @NonNull String isSearched, @NonNull String isFavorite, @NonNull String note, @NonNull ArrayList<WordContent> contents) {
        this.name = name;
        this.pronounce = pronounce;
        this.isSearched = isSearched;
        this.isFavorite = isFavorite;
        this.note = note;
        this.contents = contents;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getPronounce() {
        return pronounce;
    }

    @NonNull
    public String getIsSearched() {
        return isSearched;
    }

    @NonNull
    public String getIsFavorite() {
        return isFavorite;
    }

    @NonNull
    public String getNote() {
        return note;
    }

    @NonNull
    public ArrayList<WordContent> getContents() {
        return contents;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setPronounce(@NonNull String pronounce) {
        this.pronounce = pronounce;
    }

    public void setIsSearched(@NonNull String isSearched) {
        this.isSearched = isSearched;
    }

    public void setIsFavorite(@NonNull String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void setNote(@NonNull String note) {
        this.note = note;
    }

    public void setContents(@NonNull ArrayList<WordContent> contents) {
        this.contents = contents;
    }
}

