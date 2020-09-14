package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ContentWordAdapter;
import com.example.myapplication.adapter.SearchedWordAdapter;
import com.example.myapplication.db.model.SearchedWord;
import com.example.myapplication.db.model.TitleWord;
import com.example.myapplication.db.viewmodel.SearchedWordViewModel;
import com.example.myapplication.db.viewmodel.TitleWordViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SearchedWordAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecyclerView = findViewById(R.id.list_recycler_view);


        final Context context = this;


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        if (name.equals("searchedList")) {
            final ArrayList<SearchedWord> searchedWords = new ArrayList<>();
            SearchedWordViewModel mSearchedWordViewModel = ViewModelProviders.of(this).get(SearchedWordViewModel.class);
            mSearchedWordViewModel.getAllSearchedWords().observe(this, new Observer<List<SearchedWord>>() {
                @Override
                public void onChanged(List<SearchedWord> searchedWordList) {
                    searchedWords.addAll(searchedWordList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    mAdapter = new SearchedWordAdapter(context, searchedWords);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }




    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}