package com.example.dictdev.db.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<ImageWord> fromString(String value) {
        Type listType = new TypeToken<ArrayList<ImageWord>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayLisr(ArrayList<ImageWord> list) {
        String json = new Gson().toJson(list);
        return json;
    }
}
