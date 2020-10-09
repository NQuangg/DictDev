package com.example.myapplication.db.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myapplication.db.model.ImageWord;

import java.util.ArrayList;

@Entity(tableName = "content_word_table")
public class ContentWord {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "stand_for")
    private String standFor;

    @NonNull
    @ColumnInfo(name = "type")
    private String type;

    @NonNull
    @ColumnInfo(name = "meaning")
    private String meaning;

    @NonNull
    @ColumnInfo(name = "definition")
    private String definition;

    @NonNull
    @ColumnInfo(name = "images")
    private ArrayList<ImageWord> images;

    public ContentWord(@NonNull String name, @NonNull String standFor, @NonNull String type, @NonNull String meaning, @NonNull String definition, @NonNull ArrayList<ImageWord> images) {
        this.name = name;
        this.standFor = standFor;
        this.type = type;
        this.meaning = meaning;
        this.definition = definition;
        this.images = images;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getStandFor() {
        return standFor;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    public String getMeaning() {
        return meaning;
    }

    @NonNull
    public String getDefinition() {
        return definition;
    }

    @NonNull
    public ArrayList<ImageWord> getImages() {
        return images;
    }
}
