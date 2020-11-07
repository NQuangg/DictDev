package com.quang.dictdev.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.quang.dictdev.R;
import com.quang.dictdev.adapter.WordContentAdapter;
import com.quang.dictdev.db.model.entity.Word;
import com.quang.dictdev.db.viewmodel.WordViewModel;

import java.util.Locale;

public class WordFragment extends Fragment {
    private TextView tvWordName;
    private TextView tvWordPronounce;
    private Button btnVolume;
    private RecyclerView rvWordContent;

    TextToSpeech textToSpeech;

    public WordFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_word, container, false);
        tvWordName = rootView.findViewById(R.id.tv_word_name);
        tvWordPronounce = rootView.findViewById(R.id.tv_word_pronounce);
        btnVolume = rootView.findViewById(R.id.btn_volume);
        rvWordContent = rootView.findViewById(R.id.rv_word_content);

        String inputText = "";
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            inputText = intent.getStringExtra("inputText");
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvWordContent.setLayoutManager(linearLayoutManager);

        final WordContentAdapter mAdapter = new WordContentAdapter(getContext());
        WordViewModel mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWordViewModel.getWord(inputText).observe(getActivity(), new Observer<Word>() {
            @Override
            public void onChanged(Word word) {
                tvWordName.setText(word.getName());
                tvWordPronounce.setText(word.getPronounce());
                if (tvWordPronounce.getText().toString().isEmpty()) tvWordPronounce.setVisibility(View.GONE);

                mAdapter.setContentWords(word.getContents());
                rvWordContent.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });


        // Volume
        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
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

        return rootView;
    }
}