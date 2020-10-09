package com.example.myapplication.activity;

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
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.WordListAdapter;
import com.example.myapplication.db.model.entity.FavoriteWord;
import com.example.myapplication.db.model.entity.SearchedWord;
import com.example.myapplication.db.viewmodel.FavoriteWordViewModel;
import com.example.myapplication.db.viewmodel.SearchedWordViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ConstraintLayout listLayout;
    private boolean isUndo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listLayout = findViewById(R.id.list_layout);
        mRecyclerView = findViewById(R.id.list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        if (name.equals("searchedList")) {
            final WordListAdapter<SearchedWord> adapter = new WordListAdapter<>(this);
            mRecyclerView.setAdapter(adapter);
            final ArrayList<SearchedWord> searchedWordArrayList = new ArrayList<>();

            final SearchedWordViewModel mSearchedWordViewModel = ViewModelProviders.of(this).get(SearchedWordViewModel.class);
            mSearchedWordViewModel.getAllSearchedWords().observe(this, new Observer<List<SearchedWord>>() {
                @Override
                public void onChanged(List<SearchedWord> searchedWords) {
                    adapter.setWords(searchedWords);
                    searchedWordArrayList.addAll(searchedWords);
                }
            });

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
                            final SearchedWord myWord = adapter.getWordAtPosition(position);

                            searchedWordArrayList.remove(position);
                            adapter.setWords(searchedWordArrayList);

                            Snackbar snackbar = Snackbar.make(listLayout, "", 4000)
                                .setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        searchedWordArrayList.add(position, myWord);
                                        adapter.setWords(searchedWordArrayList);
                                        isUndo = true;
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorSnackbarText));

                            snackbar.show();
                            snackbar.addCallback(new Snackbar.Callback(){
                                @Override
                                public void onDismissed(Snackbar transientBottomBar, int event) {                                    super.onDismissed(transientBottomBar, event);
                                    super.onDismissed(transientBottomBar, event);
                                    if (!isUndo) {
                                        mSearchedWordViewModel.delete(myWord);
                                    }
                                    isUndo = false;
                                }
                            });

                        }
                    });

            helper.attachToRecyclerView(mRecyclerView);

        } else {
            final WordListAdapter<FavoriteWord> adapter = new WordListAdapter(this);
            mRecyclerView.setAdapter(adapter);
            final ArrayList<FavoriteWord> favoriteWordArrayList = new ArrayList<>();

            final FavoriteWordViewModel mFavoriteWordViewModel = ViewModelProviders.of(this).get(FavoriteWordViewModel.class);
            mFavoriteWordViewModel.getAllFavoriteWords().observe(this, new Observer<List<FavoriteWord>>() {
                @Override
                public void onChanged(List<FavoriteWord> favoriteWords) {
                    adapter.setWords(favoriteWords);
                    favoriteWordArrayList.addAll(favoriteWords);
                }
            });

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
                            final FavoriteWord myWord = adapter.getWordAtPosition(position);
                            Toast.makeText(ListActivity.this, "Deleting successful", Toast.LENGTH_SHORT).show();

                            favoriteWordArrayList.remove(position);
                            adapter.setWords(favoriteWordArrayList);

                            Snackbar snackbar = Snackbar.make(listLayout, "", 4000)
                                    .setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            favoriteWordArrayList.add(position, myWord);
                                            adapter.setWords(favoriteWordArrayList);
                                            isUndo = true;
                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(R.color.colorSnackbarText));

                            snackbar.show();
                            snackbar.addCallback(new Snackbar.Callback(){
                                @Override
                                public void onDismissed(Snackbar transientBottomBar, int event) {                                    super.onDismissed(transientBottomBar, event);
                                    super.onDismissed(transientBottomBar, event);
                                    if (!isUndo) {
                                        mFavoriteWordViewModel.delete(myWord);
                                    }
                                    isUndo = false;
                                }
                            });
                        }
                    });

            helper.attachToRecyclerView(mRecyclerView);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}