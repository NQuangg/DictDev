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
import com.example.dictdev.db.model.entity.WordNote;
import com.example.dictdev.db.viewmodel.WordNoteViewModel;

public class NoteFragment extends Fragment {
    String inputText = "";

    private EditText etNote;
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

        WordNoteViewModel mWordNoteViewModel = ViewModelProviders.of(this).get(WordNoteViewModel.class);
        mWordNoteViewModel.getNote(inputText).observe(getActivity(), new Observer<WordNote>() {
                @Override
                public void onChanged(WordNote wordNote) {
                    if (wordNote != null) {
                        etNote.setText(wordNote.getNote());
                    } else {
                        etNote.setText("");
                    }
                }
        });
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        WordNoteViewModel mWordNoteViewModel = ViewModelProviders.of(this).get(WordNoteViewModel.class);
        mWordNoteViewModel.insert(new WordNote(inputText, etNote.getText().toString()));
    }
}