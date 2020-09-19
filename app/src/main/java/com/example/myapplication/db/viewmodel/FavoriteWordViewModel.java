package com.example.myapplication.db.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.db.model.FavoriteWord;
import com.example.myapplication.db.model.SearchedWord;
import com.example.myapplication.db.repository.FavoriteWordRepository;
import com.example.myapplication.db.repository.SearchedWordRepository;

import java.util.List;

public class FavoriteWordViewModel extends AndroidViewModel {
    private FavoriteWordRepository mFavoriteWordRepository;
    private LiveData<List<FavoriteWord>> mAllFavoriteWords;

    public FavoriteWordViewModel(Application application) {
        super(application);
        mFavoriteWordRepository = new FavoriteWordRepository(application);
        mAllFavoriteWords = mFavoriteWordRepository.getAllFavoriteWords();
    }

    public LiveData<List<FavoriteWord>> getFavoriteWords(String nameWord) { return mFavoriteWordRepository.getFavoriteWords(nameWord); }

    public LiveData<List<FavoriteWord>> getAllFavoriteWords() { return mAllFavoriteWords; }

    public void insert(FavoriteWord favoriteWord) {
        mFavoriteWordRepository.insert(favoriteWord);
    }

    public void delete(FavoriteWord favoriteWord) {
        mFavoriteWordRepository.delete(favoriteWord);
    }
}