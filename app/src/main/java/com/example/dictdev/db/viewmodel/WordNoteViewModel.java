package com.example.dictdev.db.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dictdev.db.model.entity.FavoriteWord;
import com.example.dictdev.db.model.entity.WordNote;
import com.example.dictdev.db.repository.FavoriteWordRepository;
import com.example.dictdev.db.repository.WordNoteRepository;

import java.util.List;

public class WordNoteViewModel extends AndroidViewModel {
    private WordNoteRepository mWordNoteRepository;

    public WordNoteViewModel(Application application) {
        super(application);
        mWordNoteRepository = new WordNoteRepository(application);
    }

    public void insert(WordNote wordNote) {
        mWordNoteRepository.insert(wordNote);
    }

    public LiveData<WordNote> getNote(String word) {
        return mWordNoteRepository.getNote(word);
    }
}
