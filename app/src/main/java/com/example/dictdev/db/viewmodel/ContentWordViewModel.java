package com.example.dictdev.db.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dictdev.db.model.entity.ContentWord;
import com.example.dictdev.db.repository.ContentWordRepository;

import java.util.List;

public class ContentWordViewModel extends AndroidViewModel {
    private ContentWordRepository mContentWordRepository;


    public ContentWordViewModel (Application application) {
        super(application);
        mContentWordRepository = new ContentWordRepository(application);
    }

    public LiveData<List<ContentWord>> getContentWords(String nameWord) {
        return mContentWordRepository.getContentWords(nameWord);
    }
}
