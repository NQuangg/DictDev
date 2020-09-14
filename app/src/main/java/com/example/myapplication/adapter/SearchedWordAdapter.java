package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.WordActivity;
import com.example.myapplication.db.model.SearchedWord;

import java.util.ArrayList;

public class SearchedWordAdapter extends RecyclerView.Adapter<SearchedWordAdapter.ViewHolder>{
    private ArrayList<SearchedWord> mWords;
    private Context mContext;

    public SearchedWordAdapter(Context mContext, ArrayList<SearchedWord> mWords) {
        this.mWords = mWords;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SearchedWordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchedWordAdapter.ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.recycler_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedWordAdapter.ViewHolder holder, int position) {
        SearchedWord currentWord = mWords.get(position);

        holder.wordButton.setText(currentWord.getWord());
    }

    @Override
    public int getItemCount() {
        return mWords.size();
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
