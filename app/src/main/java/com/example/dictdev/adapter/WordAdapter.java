package com.example.dictdev.adapter;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictdev.R;
import com.example.dictdev.activity.WordActivity;

import java.util.ArrayList;
import java.util.Locale;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder>{
    private ArrayList<String> mWordNames;
    private Context mContext;
    private TextToSpeech textToSpeech;

    public WordAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public WordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WordAdapter.ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.item_word, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WordAdapter.ViewHolder holder, int position) {
        String currentWord = mWordNames.get(position);

        holder.word.setText(currentWord);
    }

    public void setWords(ArrayList<String> mWords) {
        this.mWordNames = mWords;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mWordNames != null)
            return mWordNames.size();
        else return 0;
    }

    public String getWordAtPosition(int position) {
        return mWordNames.get(position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layout;
        private TextView word;
        private Button volumeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            word = itemView.findViewById(R.id.text_view_word);
            volumeButton = itemView.findViewById(R.id.btn_volume);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WordActivity.class);
                    intent.putExtra("inputText", word.getText());
                    mContext.startActivity(intent);
                }
            });

            textToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status == TextToSpeech.SUCCESS) {
                        textToSpeech.setLanguage(Locale.US);
                    }
                }
            });

            volumeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textToSpeech.speak(word.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            });
        }
    }
}
