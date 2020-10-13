package com.example.dictdev.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dictdev.db.MyDatabase;
import com.example.dictdev.db.dao.ContentWordDao;
import com.example.dictdev.db.model.entity.ContentWord;

import java.util.List;

public class ContentWordRepository  {
    private MyDatabase db;
    private ContentWordDao mContentWordDao;

    public ContentWordRepository(Application application) {
        db = MyDatabase.getInstance(application);
        mContentWordDao = db.contentWordDao();
    }

    public LiveData<List<ContentWord>> getContentWords(String nameWord) {
        return db.contentWordDao().getContentWords(nameWord);
    }
}
