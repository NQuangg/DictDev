package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.model.ContentWord;

import java.util.List;

public class ContentWordAdapter extends RecyclerView.Adapter<ContentWordAdapter.ViewHolder>{
    private List<ContentWord> mContentWords;
    private Context mContext;

    public ContentWordAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ContentWordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.word_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContentWordAdapter.ViewHolder holder, int position) {
        ContentWord currentContent = mContentWords.get(position);

        holder.bindTo(currentContent);
    }

    public void setContentWords(List<ContentWord> mWords) {
        this.mContentWords = mWords;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mContentWords.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView standForWord;
        private TextView typeWord;
        private TextView meaningWord;
        private TextView definitionWord;
        private TextView exampleWord;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            standForWord = itemView.findViewById(R.id.text_view_word_stand_for);
            typeWord = itemView.findViewById(R.id.text_view_word_type);
            meaningWord = itemView.findViewById(R.id.text_view_word_meaning);
            definitionWord = itemView.findViewById(R.id.text_view_word_definition);
            exampleWord = itemView.findViewById(R.id.text_view_word_example);
        }

        void bindTo(ContentWord contentWord){
            addData(standForWord, contentWord.getStandFor());
            addData(typeWord, contentWord.getType());
            addData(meaningWord, contentWord.getMeaning());
            addData(definitionWord, contentWord.getDefinition());
            addData(exampleWord, contentWord.getExample());
        }

        void addData(TextView textView, String data) {
            if (data.isEmpty()) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setText(data);
            }
        }
    }
}
