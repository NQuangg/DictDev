package com.example.dictdev.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dictdev.db.MyDatabase;
import com.example.dictdev.db.dao.TitleWordDao;
import com.example.dictdev.db.model.entity.TitleWord;

import java.util.List;

public class TitleWordRepository {
    private MyDatabase db;
    private TitleWordDao mTitleWordDao;
    private LiveData<List<TitleWord>> mAllTitleWords;

    public TitleWordRepository(Application application) {
        db = MyDatabase.getInstance(application);
        mTitleWordDao = db.titleWordDao();
        mAllTitleWords = mTitleWordDao.getAllTitleWords();
    }

    public LiveData<TitleWord> getTitleWord(String nameWord) {
        return db.titleWordDao().getTitleWord(nameWord);
    }

    public LiveData<List<TitleWord>> getAllTitleWords() {
        return mAllTitleWords;
    }
}
