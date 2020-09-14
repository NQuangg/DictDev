package com.example.myapplication.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.db.MyDatabase;
import com.example.myapplication.db.dao.TitleWordDao;
import com.example.myapplication.db.model.TitleWord;

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
