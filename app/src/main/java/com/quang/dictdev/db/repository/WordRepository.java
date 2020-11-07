package com.quang.dictdev.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.quang.dictdev.db.MyDatabase;
import com.quang.dictdev.db.dao.WordDao;
import com.quang.dictdev.db.model.FavoriteWord;
import com.quang.dictdev.db.model.SearchedWord;
import com.quang.dictdev.db.model.WordNote;
import com.quang.dictdev.db.model.entity.Word;

import java.util.List;

public class WordRepository {
    private MyDatabase db;
    private WordDao mWordDao;

    public WordRepository(Application application) {
        db = MyDatabase.getInstance(application);
        mWordDao = db.wordDao();
    }

    // get word name
    public LiveData<List<String>> getAllWordNames() {
        return mWordDao.getAllWordNames();
    }

    // get word
    public LiveData<Word> getWord(String wordName) {
        return mWordDao.getWord(wordName);
    }


    // update searchedWord
    public void updateSearchedWord(SearchedWord searchedWord) {
        new UpdateSearchedAsyncTask(mWordDao).execute(searchedWord);
    }

    // get searchedWord
    public LiveData<List<String>> getAllSearchedWordNames() {
        return mWordDao.getAllSearchedWordNames();
    }

    // update favoriteWord
    public void updateSearchedWord(FavoriteWord favoriteWord) {
        new UpdateFavroiteAsyncTask(mWordDao).execute(favoriteWord);
    }

    // get favorite
    public LiveData<String> getFavorite(String nameWord) {
        return mWordDao.getFavorite(nameWord);
    }

    // get all favoriteWords
    public LiveData<List<String>> getAllFavoriteWordNames() {
        return mWordDao.getAllFavoriteWordNames();
    }

    // set note
    public void setNote(WordNote wordNote) {
        new UpdateNoteAsyncTask(mWordDao).execute(wordNote);
    }

    // get note
    public LiveData<String> getNote(String nameWord) {
        return mWordDao.getNote(nameWord);
    }

    private static class UpdateSearchedAsyncTask extends AsyncTask<SearchedWord, Void, Void> {
        private WordDao mAsyncTaskDao;

        private UpdateSearchedAsyncTask(WordDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(SearchedWord... searchedWords) {
            String name = searchedWords[0].getName();
            String isSearched = searchedWords[0].getIsSearched();
            mAsyncTaskDao.updateSearchedWord(name, isSearched);
            return null;
        }
    }

    private static class UpdateFavroiteAsyncTask extends AsyncTask<FavoriteWord, Void, Void> {
        private WordDao mAsyncTaskDao;

        private UpdateFavroiteAsyncTask(WordDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(FavoriteWord... favoriteWords) {
            String name = favoriteWords[0].getName();
            String isFavorite = favoriteWords[0].getIsFavorite();
            mAsyncTaskDao.updateFavoriteWord(name, isFavorite);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<WordNote, Void, Void> {
        private WordDao mAsyncTaskDao;

        private UpdateNoteAsyncTask(WordDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(WordNote... wordNotes) {
            String name = wordNotes[0].getName();
            String note = wordNotes[0].getNote();
            mAsyncTaskDao.setNote(name, note);
            return null;
        }
    }
}
