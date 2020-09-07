package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.Meaning;
import com.example.myapplication.adapter.MeaningAdapter;
import com.example.myapplication.model.ContentWord;
import com.example.myapplication.model.TitleWord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class WordActivity extends AppCompatActivity {
    private TextView nameWord;
    private TextView pronounceWord;
    private TextView abbreviationWord;
    private ImageButton volumeButton;

    private SearchView searchView;
    private ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    private RecyclerView mRecyclerView;
    private MeaningAdapter mAdapter;
    ArrayList<Meaning> meaningArrayList;

    private boolean checkSearchItem = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        nameWord = findViewById(R.id.nameWord);
        pronounceWord = findViewById(R.id.pronounceWord);
        abbreviationWord = findViewById(R.id.abbreviationWord);
        volumeButton = findViewById(R.id.volumeButton);

        Gson gson = new Gson();
        ArrayList<TitleWord> titleWords = gson.fromJson(FileRaw.readTextFile(getResources().openRawResource(R.raw.word)), new TypeToken<ArrayList<TitleWord>>(){}.getType());
        ArrayList<ContentWord> contentWords = gson.fromJson(FileRaw.readTextFile(getResources().openRawResource(R.raw.meaning)), new TypeToken<ArrayList<ContentWord>>(){}.getType());

        Intent intent = getIntent();
        String inputText = intent.getStringExtra("inputText");

        // Data is displayed
        String name = "";
        String pronounce = "";
        String abbreviation = "";

        for (TitleWord titleWord : titleWords) {
            if (inputText.equals(titleWord.getName()) || inputText.equals(titleWord.getAbbreviation())) {
                name = titleWord.getName();
                pronounce = titleWord.getPronounce();
                abbreviation = titleWord.getAbbreviation();
            }
        }
        nameWord.setText(name);
        pronounceWord.setText(pronounce);
        abbreviationWord.setText("(abbreviation " + abbreviation +")");

        meaningArrayList = new ArrayList<>();
        for (ContentWord contentWord : contentWords) {
            if (nameWord.getText().equals(contentWord.getName())) {
                meaningArrayList.add(new Meaning(contentWord.getType(), contentWord.getMeaning(), contentWord.getDefinition(), contentWord.getExample()));
            }
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MeaningAdapter(this, meaningArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        // searchView
        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
        list = new ArrayList<>();
        for (TitleWord titleWord : titleWords) {
            list.add(titleWord.getName());
            list.add(titleWord.getAbbreviation());
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

        searchView.setVisibility(View.GONE);
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
                    nameWord.setVisibility(View.VISIBLE);
                    pronounceWord.setVisibility(View.VISIBLE);
                    abbreviationWord.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    volumeButton.setVisibility(View.VISIBLE);
                } else {
                    adapter.getFilter().filter(s);
                    listView.setVisibility(View.VISIBLE);
                    nameWord.setVisibility(View.GONE);
                    pronounceWord.setVisibility(View.GONE);
                    abbreviationWord.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    volumeButton.setVisibility(View.GONE);
                }
                return false;
            }
        });

    }

    // setting menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_word, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                if (checkSearchItem) {
                    searchView.setVisibility(View.VISIBLE);
                    checkSearchItem = false;
                } else {
                    searchView.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    nameWord.setVisibility(View.VISIBLE);
                    pronounceWord.setVisibility(View.VISIBLE);
                    abbreviationWord.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    volumeButton.setVisibility(View.VISIBLE);
                    checkSearchItem = true;
                }
                return true;
            case R.id.favor:
                item.setIcon(R.drawable.ic_checked_favor);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}