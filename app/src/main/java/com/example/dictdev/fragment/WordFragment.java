package com.example.dictdev.fragment;

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

import com.example.dictdev.R;
import com.example.dictdev.adapter.ContentWordAdapter;
import com.example.dictdev.db.model.entity.ContentWord;
import com.example.dictdev.db.model.entity.TitleWord;
import com.example.dictdev.db.viewmodel.ContentWordViewModel;
import com.example.dictdev.db.viewmodel.TitleWordViewModel;

import java.util.List;
import java.util.Locale;

public class WordFragment extends Fragment {
    private TextView tvWordName;
    private TextView tvWordPronounce;
    private Button btnVolume;
    private RecyclerView rvWordContent;

    private TextToSpeech textToSpeech;

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

        rvWordContent.setLayoutManager(new LinearLayoutManager(getContext()));

        // Data is displayed
        TitleWordViewModel mTitleWordViewModel = ViewModelProviders.of(this).get(TitleWordViewModel.class);
        mTitleWordViewModel.getTitleWord(inputText).observe(getActivity(), new Observer<TitleWord>() {
            @Override
            public void onChanged(TitleWord titleWord) {
                tvWordName.setText(titleWord.getName());
                tvWordPronounce.setText(titleWord.getPronounce());
                if (tvWordPronounce.getText().toString().isEmpty()) tvWordPronounce.setVisibility(View.GONE);
            }
        });

        final ContentWordAdapter mAdapter = new ContentWordAdapter(getContext());
        ContentWordViewModel mContentWordViewModel = ViewModelProviders.of(this).get(ContentWordViewModel.class);
        mContentWordViewModel.getContentWords(inputText).observe(getActivity(), new Observer<List<ContentWord>>() {
            @Override
            public void onChanged(List<ContentWord> contentWords) {
                mAdapter.setContentWords(contentWords);
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