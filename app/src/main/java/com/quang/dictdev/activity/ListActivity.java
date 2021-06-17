package com.quang.dictdev.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.quang.dictdev.R;
import com.quang.dictdev.adapter.WordAdapter;
import com.quang.dictdev.db.model.FavoriteWord;
import com.quang.dictdev.db.model.SearchedWord;
import com.quang.dictdev.db.viewmodel.WordViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ConstraintLayout listLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mRecyclerView = findViewById(R.id.list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final WordAdapter adapter = new WordAdapter(this);
        mRecyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        final WordViewModel mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        final ArrayList<String> searchedWords = new ArrayList<>();
        final ArrayList<String> favoriteWords = new ArrayList<>();

        final boolean isSearchedList = name.equals("searchedList");

        if (isSearchedList) {
            actionBar.setTitle("Từ đã tra");
            mWordViewModel.getAllSearchedWordNames().observe(this, new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> searchedWordArrayList) {
                    searchedWords.addAll(searchedWordArrayList);
                    adapter.setWords(searchedWords);
                }
            });
        } else {
            actionBar.setTitle("Từ của bạn");
            mWordViewModel.getAllFavoriteWordNames().observe(this, new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> favoriteWordArrayList) {
                    favoriteWords.addAll(favoriteWordArrayList);
                    adapter.setWords(favoriteWords);
                }
            });
        }


        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        final int position = viewHolder.getAdapterPosition();
                        final String myWord = adapter.getWordAtPosition(position);

                        if (isSearchedList) {
                            searchedWords.remove(position);
                            mWordViewModel.updateSearchedWord(new SearchedWord(myWord, "0"));
                        } else {
                            favoriteWords.remove(position);
                            mWordViewModel.updateFavoriteWord(new FavoriteWord(myWord, "0"));
                        }
                        adapter.notifyItemRemoved(position);
                    }
                });

        helper.attachToRecyclerView(mRecyclerView);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}