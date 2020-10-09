package com.example.myapplication.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.db.MyDatabase;
import com.example.myapplication.db.dao.FavoriteWordDao;
import com.example.myapplication.db.model.entity.FavoriteWord;

import java.util.List;

public class FavoriteWordRepository {
    private MyDatabase db;
    private FavoriteWordDao mFavoriteWordDao;
    private LiveData<List<FavoriteWord>> mAllFavoriteWords;

    public FavoriteWordRepository(Application application) {
        db = MyDatabase.getInstance(application);
        mFavoriteWordDao = db.favoriteWordDao();
        mAllFavoriteWords = mFavoriteWordDao.getAllFavoriteWords();
    }

    public LiveData<List<FavoriteWord>> getFavoriteWords(String nameWord) {
        return db.favoriteWordDao().getFavoriteWords(nameWord);
    }

    public LiveData<List<FavoriteWord>> getAllFavoriteWords() {
        return mAllFavoriteWords;
    }

    public void insert(FavoriteWord favoriteWord) {
        new insertAsyncTask(mFavoriteWordDao).execute(favoriteWord);
    }

    public void delete(FavoriteWord favoriteWord) {
        new deleteAsyncTask(mFavoriteWordDao).execute(favoriteWord);
    }

    private static class insertAsyncTask extends AsyncTask<FavoriteWord, Void, Void> {

        private FavoriteWordDao mAsyncTaskDao;

        insertAsyncTask(FavoriteWordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FavoriteWord... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<FavoriteWord, Void, Void> {

        private FavoriteWordDao mAsyncTaskDao;

        deleteAsyncTask(FavoriteWordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FavoriteWord... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
