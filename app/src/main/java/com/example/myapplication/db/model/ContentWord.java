package com.example.myapplication.db.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    @ColumnInfo(name = "example")
    private String example;

    public ContentWord(@NonNull int id, @NonNull String name, @NonNull String standFor, @NonNull String type, @NonNull String meaning, @NonNull String definition, @NonNull String example) {
        this.id = id;
        this.name = name;
        this.standFor = standFor;
        this.type = type;
        this.meaning = meaning;
        this.definition = definition;
        this.example = example;
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
    public String getExample() {
        return example;
    }
}
