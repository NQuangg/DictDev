package com.quang.dictdev.db.model;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WordContent {
    private String abbreviation;

    private String standFor;

    private String type;

    private String meaning;

    private String definition;

    private ArrayList<WordImage> images;
}
