package com.example.myapplication.db.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.db.model.TitleWord;
import com.example.myapplication.db.repository.TitleWordRepository;

import java.util.List;


public class TitleWordViewModel extends AndroidViewModel {
    private TitleWordRepository mTitleWordRepository;
    private LiveData<List<TitleWord>> mAllTitleWords;

    public TitleWordViewModel (Application application) {
        super(application);
        mTitleWordRepository = new TitleWordRepository(application);
        mAllTitleWords = mTitleWordRepository.getAllTitleWords();
    }

    public LiveData<TitleWord> getTitleWord(String nameWord) {
        return mTitleWordRepository.getTitleWord(nameWord);
    }

    public LiveData<List<TitleWord>> getAllTitleWords() { return mAllTitleWords; }
}