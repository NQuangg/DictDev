package com.example.dictdev.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.dictdev.db.MyDatabase;
import com.example.dictdev.db.dao.SearchedWordDao;
import com.example.dictdev.db.dao.WordNoteDao;
import com.example.dictdev.db.model.entity.SearchedWord;
import com.example.dictdev.db.model.entity.WordNote;


public class WordNoteRepository {
    private MyDatabase db;
    private WordNoteDao mWordNoteDao;

    public WordNoteRepository(Application application) {
        db = MyDatabase.getInstance(application);
        mWordNoteDao = db.wordNoteDao();
    }

    public void insert(WordNote wordNote) {
        new WordNoteRepository.insertAsyncTask(mWordNoteDao).execute(wordNote);
    }

    public LiveData<WordNote> getNote(String word) {
        return mWordNoteDao.getNote(word);
    }


    private static class insertAsyncTask extends AsyncTask<WordNote, Void, Void> {

        private WordNoteDao mAsyncTaskDao;

        insertAsyncTask(WordNoteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WordNote... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
