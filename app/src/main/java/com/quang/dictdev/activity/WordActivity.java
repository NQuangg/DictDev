package com.quang.dictdev.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.quang.dictdev.R;
import com.quang.dictdev.adapter.PagerAdapter;
import com.quang.dictdev.db.model.FavoriteWord;
import com.quang.dictdev.db.model.SearchedWord;
import com.quang.dictdev.db.model.WordContent;
import com.quang.dictdev.db.model.entity.Word;
import com.quang.dictdev.db.viewmodel.WordViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class WordActivity extends AppCompatActivity {
    private ViewGroup layout;
    private SearchView svWord;
    private ListView lvWord;

    boolean isChecked = true;
    boolean isFavorite = false;
    String inputText = "";
    WordViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        layout = findViewById(R.id.layout_activity_word);

        Intent intent = getIntent();
        if (intent != null) {
            inputText = intent.getStringExtra("inputText");
        }

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWordViewModel.getWord(inputText).observe(this, new Observer<Word>() {
            @Override
            public void onChanged(Word word) {
                ArrayList<WordContent> wordContents = word.getContents();
                WordContent wordContent = wordContents.get(0);
                if (wordContent.getAbbreviation().isEmpty()) {
                    setTitle(inputText);
                } else {
                    setTitle(wordContent.getAbbreviation());
                }
            }
        });

        // searchView
        svWord = findViewById(R.id.sv_word);
        lvWord = findViewById(R.id.lv_word);

        final ArrayList<String> list = new ArrayList<>();
        mWordViewModel.getAllWordNames().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> wordNames) {
                for (String wordname: wordNames) {
                    list.add(wordname);
                }
            }
        });

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvWord.setAdapter(adapter);

        lvWord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                SearchedWord searchedWord = new SearchedWord(lvWord.getItemAtPosition(position).toString(), "1");
                mWordViewModel.updateSearchedWord(searchedWord);
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
                        SearchedWord searchedWord = new SearchedWord(item, "1");
                        mWordViewModel.updateSearchedWord(searchedWord);
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

        // TabLayout
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("NGHĨA"));
        tabLayout.addTab(tabLayout.newTab().setText("HÌNH ẢNH"));
        tabLayout.addTab(tabLayout.newTab().setText("GHI CHÚ"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
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

        mWordViewModel.getFavorite(inputText).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String wordFavorite) {
                if (wordFavorite.equals("0")) {
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
                TransitionManager.beginDelayedTransition(layout);
                svWord.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                isChecked = !isChecked;
                return true;
            case R.id.favor:
                if (isFavorite) {
                    FavoriteWord favoriteWord = new FavoriteWord(inputText, "0");
                    mWordViewModel.updateFavoriteWord(favoriteWord);
                    item.setIcon(R.drawable.ic_unchecked_favor);
                    Toast.makeText(getApplicationContext(), "Bỏ khỏi từ của bạn", Toast.LENGTH_SHORT).show();
                    isFavorite = false;
                } else {
                    FavoriteWord favoriteWord = new FavoriteWord(inputText, "1");
                    mWordViewModel.updateFavoriteWord(favoriteWord);
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