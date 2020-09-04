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
    private Button button;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);

        String data = readTextFile(getResources().openRawResource(R.raw.wordData));
        Gson gson = new Gson();
        ArrayList<Word> words = gson.fromJson(data, new TypeToken<ArrayList<Word>>(){}.getType());


        list = new ArrayList<String>();
        for (Word word: words) {
            list.add(word.getName());
            list.add(word.getAbbreviation());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setVisibility(View.GONE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Log.d("AAAA", list.get(index));
                Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                intent.putExtra("name", list.get(index));
                startActivity(intent);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals("")) {
                    listView.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                } else {
                    adapter.getFilter().filter(s);
                    listView.setVisibility(View.VISIBLE);
                    button.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }
}