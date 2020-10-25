package com.example.dictdev.db.model.converter;

import androidx.room.TypeConverter;

import com.example.dictdev.db.model.WordImage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WordImageConverter {
    @TypeConverter
    public static ArrayList<WordImage> fromString(String value) {
        Type listType = new TypeToken<ArrayList<WordImage>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayLisr(ArrayList<WordImage> list) {
        String json = new Gson().toJson(list);
        return json;
    }
}
