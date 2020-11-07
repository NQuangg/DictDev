package com.example.dictdev.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dictdev.R;
import com.example.dictdev.adapter.WordAdapter;
import com.example.dictdev.db.model.FavoriteWord;
import com.example.dictdev.db.model.SearchedWord;
import com.example.dictdev.db.viewmodel.WordViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ConstraintLayout listLayout;

    boolean isUndo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
            mWordViewModel.getAllSearchedWordNames().observe(this, new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> searchedWordArrayList) {
                    searchedWords.addAll(searchedWordArrayList);
                    adapter.setWords(searchedWords);
                }
            });
        } else {
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

//                        Snackbar snackbar = Snackbar.make(listLayout, "", 4000)
//                                .setAction("UNDO", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        if (isSearchedList) {
//                                            searchedWords.add(position, myWord);
//                                        } else {
//                                            favoriteWords.add(position, myWord);
//                                        }
//                                        adapter.notifyDataSetChanged();
//                                        isUndo = true;
//                                    }
//                                })
//                                .setActionTextColor(getResources().getColor(R.color.colorSnackbarText));
//
//                        snackbar.show();
//                        snackbar.addCallback(new Snackbar.Callback(){
//                            @Override
//                            public void onDismissed(Snackbar transientBottomBar, int event) {                                    super.onDismissed(transientBottomBar, event);
//                                super.onDismissed(transientBottomBar, event);
//                                if (!isUndo) {
//                                    if (isSearchedList) {
//                                        mWordViewModel.updateSearchedWord(new SearchedWord(myWord, "0"));
//                                    } else {
//                                        mWordViewModel.updateFavoriteWord(new FavoriteWord(myWord, "0"));
//                                    }
//                                }
//                                isUndo = false;
//                            }
//                        });
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