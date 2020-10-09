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
import com.example.myapplication.db.model.entity.ContentWord;
import com.example.myapplication.db.model.entity.FavoriteWord;
import com.example.myapplication.db.model.entity.TitleWord;
import com.example.myapplication.db.viewmodel.ContentWordViewModel;
import com.example.myapplication.db.viewmodel.FavoriteWordViewModel;
import com.example.myapplication.db.viewmodel.TitleWordViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WordActivity extends AppCompatActivity {
    private TextView tvWordName;
    private TextView tvWordPronounce;
    private Button btnVolume;
    private SearchView svWord;
    private RecyclerView rvWordContent;

    private ListView lvWord;
    private boolean isChecked = true;
    private boolean isFavorite = false;
    private TextToSpeech textToSpeech;
    private String inputText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        tvWordName = findViewById(R.id.tv_word_name);
        tvWordPronounce = findViewById(R.id.tv_word_pronounce);
        btnVolume = findViewById(R.id.btn_volume);
        rvWordContent = findViewById(R.id.rv_word_content);

        Intent intent = getIntent();
        inputText = intent.getStringExtra("inputText");
        final Context context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rvWordContent.setLayoutManager(new LinearLayoutManager(this));

        // Data is displayed
        TitleWordViewModel mTitleWordViewModel = ViewModelProviders.of(this).get(TitleWordViewModel.class);
        mTitleWordViewModel.getTitleWord(inputText).observe(this, new Observer<TitleWord>() {
            @Override
            public void onChanged(TitleWord titleWord) {
                tvWordName.setText(titleWord.getName());
                tvWordPronounce.setText(titleWord.getPronounce());
                if (tvWordPronounce.getText().toString().isEmpty()) tvWordPronounce.setVisibility(View.GONE);
            }
        });

        final ContentWordAdapter mAdapter = new ContentWordAdapter(this);
        ContentWordViewModel mContentWordViewModel = ViewModelProviders.of(this).get(ContentWordViewModel.class);
        mContentWordViewModel.getContentWords(inputText).observe(this, new Observer<List<ContentWord>>() {
            @Override
            public void onChanged(List<ContentWord> contentWords) {
                mAdapter.setContentWords(contentWords);
                rvWordContent.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });


        // searchView
        svWord = findViewById(R.id.sv_word);
        lvWord = findViewById(R.id.lv_word);

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
                Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                intent.putExtra("inputText", lvWord.getItemAtPosition(position).toString());
                startActivity(intent);
                svWord.setQuery("", false);
            }
        });

        svWord.setVisibility(View.GONE);
        svWord.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                boolean check = false;
                for (String item : list) {
                    if (s.toLowerCase().equals(item.toLowerCase())) {
                        Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                        intent.putExtra("inputText", item);
                        startActivity(intent);
                        svWord.setQuery("", false);
                        check = true;
                    }
                }

                if (!check) {
                    Toast.makeText(getApplicationContext(), "the title_word does not exist", Toast.LENGTH_LONG).show();
                    lvWord.setVisibility(View.GONE);
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


        // Volume
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        btnVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(tvWordName.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
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
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_word, menu);

        FavoriteWordViewModel mFavoriteWordViewModel = ViewModelProviders.of(this).get(FavoriteWordViewModel.class);
        mFavoriteWordViewModel.getFavoriteWord(inputText).observe(this, new Observer<FavoriteWord>() {
            @Override
            public void onChanged(FavoriteWord favoriteWord) {
                if (favoriteWord == null) {
                    menu.getItem(1).setIcon(R.drawable.ic_unchecked_favor);
                    isFavorite = false;
                } else {
                    menu.getItem(1).setIcon(R.drawable.ic_checked_favor);
                    isFavorite = true;
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                if (isChecked) {
                    svWord.setVisibility(View.VISIBLE);
                    isChecked = false;
                } else {
                    svWord.setVisibility(View.GONE);
                    isChecked = true;
                }
                return true;
            case R.id.favor:
                FavoriteWordViewModel mFavoriteWordViewModel = ViewModelProviders.of(this).get(FavoriteWordViewModel.class);
                if (isFavorite) {
                    mFavoriteWordViewModel.delete(new FavoriteWord(inputText));
                    item.setIcon(R.drawable.ic_unchecked_favor);
                    Toast.makeText(getApplicationContext(), "Bỏ khỏi từ của bạn", Toast.LENGTH_SHORT).show();
                    isFavorite = false;
                } else {
                    mFavoriteWordViewModel.insert(new FavoriteWord(inputText));
                    item.setIcon(R.drawable.ic_checked_favor);
                    Toast.makeText(getApplicationContext(), "Thêm vào từ của bạn", Toast.LENGTH_SHORT).show();
                    isFavorite = true;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}