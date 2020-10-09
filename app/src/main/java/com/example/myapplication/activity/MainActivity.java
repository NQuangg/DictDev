package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.myapplication.R;
import com.example.myapplication.db.model.entity.FavoriteWord;
import com.example.myapplication.db.model.entity.SearchedWord;
import com.example.myapplication.db.model.entity.TitleWord;
import com.example.myapplication.db.viewmodel.FavoriteWordViewModel;
import com.example.myapplication.db.viewmodel.SearchedWordViewModel;
import com.example.myapplication.db.viewmodel.TitleWordViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SearchView svWord;
    private ListView lvWord;
    private Button btnWordSearched;
    private Button btnWordFavorite;

    private TitleWordViewModel mTitleWordViewModel;
    private SearchedWordViewModel mSearchedWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        svWord = findViewById(R.id.sv_word);
        lvWord = findViewById(R.id.lv_word);
        btnWordSearched = findViewById(R.id.btn_word_searched);
        btnWordFavorite = findViewById(R.id.btn_word_favorite);

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
        lvWord.setAdapter(adapter);
        lvWord.setVisibility(View.GONE);

        lvWord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                mSearchedWordViewModel.insert(new SearchedWord(lvWord.getItemAtPosition(position).toString()));
                Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                intent.putExtra("inputText", lvWord.getItemAtPosition(position).toString());
                startActivity(intent);
                svWord.setQuery("", false);
            }
        });


        svWord.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                boolean check = false;
                for (String item : list) {
                    if (s.toLowerCase().equals(item.toLowerCase())) {
                        mSearchedWordViewModel.insert(new SearchedWord(item));
                        Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                        intent.putExtra("inputText", item);
                        startActivity(intent);
                        svWord.setQuery("", false);
                        check = true;
                    }
                }

                if (!check) {
                    lvWord.setVisibility(View.GONE);
                    svWord.setQuery("", false);
                    Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                    intent.putExtra("unknownWord", s);
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    lvWord.setVisibility(View.GONE);
                } else {
                    adapter.getFilter().filter(s.trim());
                    lvWord.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        btnWordSearched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("name", "searchedList");
                startActivity(intent);
            }
        });

        btnWordFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("name", "favoriteList");
                startActivity(intent);
            }
        });
    }

    // setting menu
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);

        return true;
    }
}