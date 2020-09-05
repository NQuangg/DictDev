package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myapplication.model.Word;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
    private ListView listView;
    private Button searchedWordButton;
    private Button favoriteWordButton;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
        searchedWordButton = findViewById(R.id.searchedWordButton);
        favoriteWordButton = findViewById(R.id.favoriteWordButton);

        Gson gson = new Gson();
        ArrayList<Word> words = gson.fromJson(FileRaw.readTextFile(getResources().openRawResource(R.raw.word)), new TypeToken<ArrayList<Word>>(){}.getType());

        list = new ArrayList<>();
        for (Word word: words) {
            list.add(word.getName());
            list.add(word.getAbbreviation());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setVisibility(View.GONE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                intent.putExtra("inputText", listView.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                boolean check = false;
                for (String item : list) {
                    if (s.toLowerCase().equals(item.toLowerCase())) {
                        Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                        intent.putExtra("inputText", item);
                        startActivity(intent);
                        check = true;
                    }
                }

                if (!check) {
                    Toast.makeText(getApplicationContext(), "the word does not exist", Toast.LENGTH_LONG).show();
                    listView.setVisibility(View.GONE);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals("")) {
                    listView.setVisibility(View.GONE);
                    searchedWordButton.setVisibility(View.VISIBLE);
                    favoriteWordButton.setVisibility(View.VISIBLE);
                } else {
                    adapter.getFilter().filter(s);
                    listView.setVisibility(View.VISIBLE);
                    searchedWordButton.setVisibility(View.GONE);
                    favoriteWordButton.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }
}