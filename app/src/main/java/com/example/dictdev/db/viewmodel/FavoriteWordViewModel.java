package com.example.dictdev.db.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dictdev.db.model.entity.FavoriteWord;
import com.example.dictdev.db.repository.FavoriteWordRepository;

import java.util.List;

public class FavoriteWordViewModel extends AndroidViewModel {
    private FavoriteWordRepository mFavoriteWordRepository;
    private LiveData<List<FavoriteWord>> mAllFavoriteWords;

    public FavoriteWordViewModel(Application application) {
        super(application);
        mFavoriteWordRepository = new FavoriteWordRepository(application);
        mAllFavoriteWords = mFavoriteWordRepository.getAllFavoriteWords();
    }

    public LiveData<FavoriteWord> getFavoriteWord(String nameWord) { return mFavoriteWordRepository.getFavoriteWord(nameWord); }

    public LiveData<List<FavoriteWord>> getAllFavoriteWords() { return mAllFavoriteWords; }

    public void insert(FavoriteWord favoriteWord) {
        mFavoriteWordRepository.insert(favoriteWord);
    }

    public void delete(FavoriteWord favoriteWord) {
        mFavoriteWordRepository.delete(favoriteWord);
    }
}
