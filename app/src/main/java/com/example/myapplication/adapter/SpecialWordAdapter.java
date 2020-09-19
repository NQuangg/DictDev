package com.example.myapplication.adapter;

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

import com.example.myapplication.R;
import com.example.myapplication.activity.WordActivity;
import com.example.myapplication.db.model.Word;

import java.util.List;
import java.util.Locale;

public class SpecialWordAdapter<T extends Word> extends RecyclerView.Adapter<SpecialWordAdapter.ViewHolder>{
    private List<T> mWords;
    private Context mContext;
    private TextToSpeech textToSpeech;

    public SpecialWordAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SpecialWordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SpecialWordAdapter.ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.recycler_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialWordAdapter.ViewHolder holder, int position) {
        T currentWord = mWords.get(position);

        holder.word.setText(currentWord.getWord());
    }

    public void setWords(List<T> mWords) {
        this.mWords = mWords;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }

    public T getWordAtPosition(int position) {
        return mWords.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layout;
        private TextView word;
        private Button volumeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            word = itemView.findViewById(R.id.text_view_word);
            volumeButton = itemView.findViewById(R.id.button_volume);

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
