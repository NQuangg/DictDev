package com.quang.dictdev.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quang.dictdev.R;
import com.quang.dictdev.adapter.ImageAdapter;
import com.quang.dictdev.db.model.WordContent;
import com.quang.dictdev.db.model.WordImage;
import com.quang.dictdev.db.model.entity.Word;
import com.quang.dictdev.db.viewmodel.WordViewModel;

import java.util.ArrayList;

public class ImageFragment extends Fragment {
    private RecyclerView rvImage;

    public ImageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        rvImage = rootView.findViewById(R.id.rv_image);

        String inputText = "";
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            inputText = intent.getStringExtra("inputText");
        }

        final ArrayList<WordImage> imageWords = new ArrayList<>();
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        final ImageAdapter imageListAdapter = new ImageAdapter(getContext(), imageWords);
        rvImage.setLayoutManager(gridLayoutManager);

        WordViewModel mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWordViewModel.getWord(inputText).observe(getActivity(), new Observer<Word>() {
            @Override
            public void onChanged(Word word) {
                ArrayList<WordContent> wordContent = word.getContents();
                for (WordContent item: wordContent) {
                    imageWords.addAll(item.getImages());
                }
                rvImage.setAdapter(imageListAdapter);


                if (imageWords.size() == 0) {
                    rvImage.setVisibility(View.GONE);
                }
            }
        });

        return rootView;
    }

}