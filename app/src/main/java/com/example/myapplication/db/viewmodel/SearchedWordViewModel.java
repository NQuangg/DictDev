package com.example.myapplication.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.db.model.SearchedWord;
import com.example.myapplication.db.repository.SearchedWordRepository;

import java.util.List;

public class SearchedWordViewModel extends AndroidViewModel {
    private SearchedWordRepository mSearchedWordRepository;
    private LiveData<List<SearchedWord>> mAllSearchedWords;

    public SearchedWordViewModel(Application application) {
        super(application);
        mSearchedWordRepository = new SearchedWordRepository(application);
        mAllSearchedWords = mSearchedWordRepository.getAllSearchedWords();
    }

    public LiveData<List<SearchedWord>> getAllSearchedWords() { return mAllSearchedWords; }

    public void insert(SearchedWord searchedWord) {
        mSearchedWordRepository.insert(searchedWord);
    }

    public void delete(SearchedWord searchedWord) {
        mSearchedWordRepository.delete(searchedWord);
    }

}
