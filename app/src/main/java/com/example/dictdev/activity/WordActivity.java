package com.example.dictdev.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dictdev.R;
import com.example.dictdev.adapter.PagerAdapter;
import com.example.dictdev.db.model.entity.FavoriteWord;
import com.example.dictdev.db.model.entity.TitleWord;
import com.example.dictdev.db.viewmodel.FavoriteWordViewModel;
import com.example.dictdev.db.viewmodel.TitleWordViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class WordActivity extends AppCompatActivity {
    private SearchView svWord;
    private ListView lvWord;

    private boolean isChecked = true;
    private boolean isFavorite = false;
//    private TextToSpeech textToSpeech;
    private String inputText = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        Intent intent = getIntent();
        if (intent != null) {
            inputText = intent.getStringExtra("inputText");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TitleWordViewModel mTitleWordViewModel = ViewModelProviders.of(this).get(TitleWordViewModel.class);


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
        tabLayout.addTab(tabLayout.newTab().setText("GHI CHÚ"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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