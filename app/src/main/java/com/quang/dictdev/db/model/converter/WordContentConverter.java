package com.quang.dictdev.db.model.converter;

import androidx.room.TypeConverter;

import com.quang.dictdev.db.model.WordContent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WordContentConverter {
    @TypeConverter
    public static ArrayList<WordContent> fromString(String value) {
        Type listType = new TypeToken<ArrayList<WordContent>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayLisr(ArrayList<WordContent> list) {
        String json = new Gson().toJson(list);
        return json;
    }
}
