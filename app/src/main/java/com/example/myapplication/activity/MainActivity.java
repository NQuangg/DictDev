package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.db.model.SearchedWord;
import com.example.myapplication.db.model.TitleWord;
import com.example.myapplication.db.viewmodel.SearchedWordViewModel;
import com.example.myapplication.db.viewmodel.TitleWordViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
    private ListView listView;
    private Button searchedWordButton;
    private Button favoriteWordButton;

    private TitleWordViewModel mTitleWordViewModel;
    private SearchedWordViewModel mSearchedWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.list_view);
        searchedWordButton = findViewById(R.id.searched_word_button);
        favoriteWordButton = findViewById(R.id.favorite_word_button);

        mTitleWordViewModel = ViewModelProviders.of(this).get(TitleWordViewModel.class);
        mSearchedWordViewModel = ViewModelProviders.of(this).get(SearchedWordViewModel.class);

        final ArrayList<String> list = new ArrayList<>();
        mTitleWordViewModel.getAllTitleWords().observe(this, new Observer<List<TitleWord>>() {
            @Override
            public void onChanged(List<TitleWord> titleWords) {
                for (TitleWord titleWord: titleWords) {
                    list.add(titleWord.getName());
                }
            }
        });

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setVisibility(View.GONE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                mSearchedWordViewModel.insert(new SearchedWord(listView.getItemAtPosition(position).toString()));
                Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                intent.putExtra("inputText", listView.getItemAtPosition(position).toString());
                startActivity(intent);
                searchView.setQuery("", false);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                boolean check = false;
                for (String item : list) {
                    if (s.toLowerCase().equals(item.toLowerCase())) {
                        mSearchedWordViewModel.insert(new SearchedWord(item));
                        Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                        intent.putExtra("inputText", item);
                        startActivity(intent);
                        searchView.setQuery("", false);
                        check = true;
                    }
                }

                if (!check) {
                    listView.setVisibility(View.GONE);
                    searchView.setQuery("", false);
                    Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                    intent.putExtra("unknownWord", s);
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    listView.setVisibility(View.GONE);
                } else {
                    adapter.getFilter().filter(s.trim());
                    listView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        searchedWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("name", "searchedList");
                startActivity(intent);
            }
        });

        favoriteWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("name", "favoriteList");
                startActivity(intent);
            }
        });
    }



}