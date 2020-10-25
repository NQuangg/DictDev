package com.example.dictdev.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictdev.R;
import com.example.dictdev.db.model.WordContent;

import java.util.List;

public class WordContentAdapter extends RecyclerView.Adapter<WordContentAdapter.ViewHolder>{
    private List<WordContent> mWordContents;
    private Context mContext;

    public WordContentAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public WordContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.item_content_word, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WordContentAdapter.ViewHolder holder, int position) {
        WordContent currentContent = mWordContents.get(position);

        holder.bindTo(currentContent);
    }

    @Override
    public int getItemCount() {
        return mWordContents.size();
    }

    public void setContentWords(List<WordContent> mWords) {
        this.mWordContents = mWords;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvWordtype;
        private TextView tvWordstandFor;
        private TextView tvWordMeaning;
        private TextView tvWordDefinition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvWordtype = itemView.findViewById(R.id.tv_word_type);
            tvWordstandFor = itemView.findViewById(R.id.tv_word_standFor);
            tvWordMeaning = itemView.findViewById(R.id.tv_word_meaning);
            tvWordDefinition = itemView.findViewById(R.id.tv_word_definition);
        }

        void bindTo(WordContent wordContent){
            addData(tvWordtype, wordContent.getType());
            addData(tvWordstandFor, wordContent.getStandFor());
            addData(tvWordMeaning, wordContent.getMeaning());
            addData(tvWordDefinition, wordContent.getDefinition());
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
