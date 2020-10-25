package com.example.dictdev.db.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dictdev.db.model.FavoriteWord;
import com.example.dictdev.db.model.SearchedWord;
import com.example.dictdev.db.model.WordNote;
import com.example.dictdev.db.model.entity.Word;
import com.example.dictdev.db.repository.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordRepository mWordRepository;

    public WordViewModel(Application application) {
        super(application);
        mWordRepository = new WordRepository(application);
    }

    // get word name
    public LiveData<List<String>> getAllWordNames() {
        return mWordRepository.getAllWordNames();
    }

    // get word
    public LiveData<Word> getWord(String wordName) {
        return mWordRepository.getWord(wordName);
    }

    // update searchedWord
    public void updateSearchedWord(SearchedWord searchWord) {
        mWordRepository.updateSearchedWord(searchWord);
    }

    // get all searchedWords
    public LiveData<List<String>> getAllSearchedWordNames() {
        return mWordRepository.getAllSearchedWordNames();
    }


    // update favoriteWord
    public void updateFavoriteWord(FavoriteWord favoriteWord) {
        mWordRepository.updateSearchedWord(favoriteWord);
    }

    // get favorite
    public LiveData<String> getFavorite(String wordName) {
        return mWordRepository.getFavorite(wordName);
    }

    // get all favoriteWords
    public LiveData<List<String>> getAllFavoriteWordNames() {
        return mWordRepository.getAllFavoriteWordNames();
    }

    // set note
    public void setNote(WordNote wordNote) {
        mWordRepository.setNote(wordNote);
    }

    // get note
    public LiveData<String> getNote(String nameWord) {
        return mWordRepository.getNote(nameWord);
    }
}
