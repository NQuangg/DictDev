package com.quang.dictdev.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavoriteWord {
    private String name;
    private String isFavorite;
}
