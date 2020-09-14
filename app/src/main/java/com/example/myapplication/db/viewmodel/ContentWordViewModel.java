package com.example.myapplication.db.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.db.model.ContentWord;
import com.example.myapplication.db.model.TitleWord;
import com.example.myapplication.db.repository.ContentWordRepository;
import com.example.myapplication.db.repository.TitleWordRepository;

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
