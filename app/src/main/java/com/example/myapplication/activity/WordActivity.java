package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ContentWordAdapter;
import com.example.myapplication.db.model.ContentWord;
import com.example.myapplication.db.model.TitleWord;
import com.example.myapplication.db.viewmodel.ContentWordViewModel;
import com.example.myapplication.db.viewmodel.TitleWordViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WordActivity extends AppCompatActivity {
    private TextView nameWord;
    private TextView pronounceWord;
    private Button volumeButton;
    private SearchView searchView;
    private ListView listView;
    private RecyclerView mRecyclerView;
    private ContentWordAdapter mAdapter;


    private boolean checkSearchItem = true;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nameWord = findViewById(R.id.name_word);
        pronounceWord = findViewById(R.id.pronounce_word);
        volumeButton = findViewById(R.id.volume_button);
        mRecyclerView = findViewById(R.id.recycler_view);

        Intent intent = getIntent();
        String inputText = intent.getStringExtra("inputText");
        final Context context = this;



        // Data is displayed
        TitleWordViewModel mTitleWordViewModel = ViewModelProviders.of(this).get(TitleWordViewModel.class);
        mTitleWordViewModel.getTitleWord(inputText).observe(this, new Observer<TitleWord>() {
            @Override
            public void onChanged(TitleWord titleWord) {
                nameWord.setText(titleWord.getName());
                pronounceWord.setText(titleWord.getPronounce());
                if (pronounceWord.getText().toString().isEmpty()) pronounceWord.setVisibility(View.GONE);
            }
        });

        final ArrayList<ContentWord> contentWords = new ArrayList<>();
        ContentWordViewModel mContentWordViewModel = ViewModelProviders.of(this).get(ContentWordViewModel.class);
        mContentWordViewModel.getContentWords(inputText).observe(this, new Observer<List<ContentWord>>() {
            @Override
            public void onChanged(List<ContentWord> contentWordList) {
                contentWords.addAll(contentWordList);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                mAdapter = new ContentWordAdapter(context, contentWords);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });





        // searchView
        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.list_view);

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
                Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                intent.putExtra("inputText", listView.getItemAtPosition(position).toString());
                startActivity(intent);
                searchView.setQuery("", false);
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
                        searchView.setQuery("", false);
                        check = true;
                    }
                }

                if (!check) {
                    Toast.makeText(getApplicationContext(), "the title_word does not exist", Toast.LENGTH_LONG).show();
                    listView.setVisibility(View.GONE);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    listView.setVisibility(View.GONE);
                    nameWord.setVisibility(nameWord.getText().toString().isEmpty() ? View.GONE : View.VISIBLE);
                    pronounceWord.setVisibility(pronounceWord.getText().toString().isEmpty() ? View.GONE : View.VISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    volumeButton.setVisibility(View.VISIBLE);
                } else {
                    adapter.getFilter().filter(s.trim());
                    listView.setVisibility(View.VISIBLE);
                    nameWord.setVisibility(View.GONE);
                    pronounceWord.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    volumeButton.setVisibility(View.GONE);
                }
                return false;
            }
        });


        // Volume
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

        volumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(nameWord.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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