package com.example.myapplication.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.db.MyDatabase;
import com.example.myapplication.db.dao.SearchedWordDao;
import com.example.myapplication.db.model.SearchedWord;

import java.util.List;

public class SearchedWordRepository {
    private MyDatabase db;
    private SearchedWordDao mSearchedWordDao;
    private LiveData<List<SearchedWord>> mAllSearchedWords;

    public SearchedWordRepository(Application application) {
        db = MyDatabase.getInstance(application);
        mSearchedWordDao = db.searchedWordDao();
        mAllSearchedWords = mSearchedWordDao.getAllSearchedWords();
    }

    public LiveData<List<SearchedWord>> getAllSearchedWords() {
        return mAllSearchedWords;
    }

    public void insert(SearchedWord searchedWord) {
        new insertAsyncTask(mSearchedWordDao).execute(searchedWord);
    }

    public void delete(SearchedWord searchedWord) {
        new deleteAsyncTask(mSearchedWordDao).execute(searchedWord);
    }

    private static class insertAsyncTask extends AsyncTask<SearchedWord, Void, Void> {

        private SearchedWordDao mAsyncTaskDao;

        insertAsyncTask(SearchedWordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SearchedWord... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<SearchedWord, Void, Void> {

        private SearchedWordDao mAsyncTaskDao;

        deleteAsyncTask(SearchedWordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SearchedWord... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
