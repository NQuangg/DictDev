package com.example.dictdev.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.dictdev.R;
import com.example.dictdev.db.model.WordNote;
import com.example.dictdev.db.viewmodel.WordViewModel;

public class NoteFragment extends Fragment {
    private EditText etNote;

    String inputText = "";

    public NoteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note, container, false);
        etNote = rootView.findViewById(R.id.et_note);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            inputText = intent.getStringExtra("inputText");
        }

        WordViewModel mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWordViewModel.getNote(inputText).observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String wordNote) {
                etNote.setText(wordNote);
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        WordViewModel mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWordViewModel.setNote(new WordNote(inputText, etNote.getText().toString()));
    }
}