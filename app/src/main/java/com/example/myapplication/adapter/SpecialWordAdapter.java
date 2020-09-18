package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.WordActivity;
import com.example.myapplication.db.model.Word;

import java.util.ArrayList;
import java.util.List;

public class SpecialWordAdapter<T extends Word> extends RecyclerView.Adapter<SpecialWordAdapter.ViewHolder>{
    private List<T> mWords;
    private Context mContext;

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

        holder.wordButton.setText(currentWord.getWord());
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
        private Button wordButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wordButton = itemView.findViewById(R.id.word_button);

            wordButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WordActivity.class);
                    intent.putExtra("inputText", wordButton.getText());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
