package com.example.myapplication.adapter;

import lombok.Data;

@Data
public class Meaning {
    private String type;
    private String meaning;
    private String definition;
    private String example;

    public Meaning(String type, String meaning, String definition, String example) {
        this.type = type;
        this.meaning = meaning;
        this.definition = definition;
        this.example = example;
    }
}
