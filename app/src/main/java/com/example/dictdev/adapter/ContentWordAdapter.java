package com.example.dictdev.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictdev.R;
import com.example.dictdev.db.model.ImageWord;
import com.example.dictdev.db.model.entity.ContentWord;

import java.util.ArrayList;
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
                inflate(R.layout.content_word_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContentWordAdapter.ViewHolder holder, int position) {
        ContentWord currentContent = mContentWords.get(position);

        holder.bindTo(currentContent);
    }

    @Override
    public int getItemCount() {
        return mContentWords.size();
    }

    public void setContentWords(List<ContentWord> mWords) {
        this.mContentWords = mWords;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvWordtype;
        private TextView tvWordstandFor;
        private TextView tvWordMeaning;
        private TextView tvWordDefinition;
        private RecyclerView rvImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvWordtype = itemView.findViewById(R.id.tv_word_type);
            tvWordstandFor = itemView.findViewById(R.id.tv_word_standFor);
            tvWordMeaning = itemView.findViewById(R.id.tv_word_meaning);
            tvWordDefinition = itemView.findViewById(R.id.tv_word_definition);
            rvImage = itemView.findViewById(R.id.rv_image);
        }

        void bindTo(ContentWord contentWord){
            addData(tvWordtype, contentWord.getType());
            addData(tvWordstandFor, contentWord.getStandFor());
            addData(tvWordMeaning, contentWord.getMeaning());
            addData(tvWordDefinition, contentWord.getDefinition());

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            rvImage.setLayoutManager(linearLayoutManager);

            ArrayList<ImageWord> imageWords = contentWord.getImages();
            if (!imageWords.isEmpty()) {
                ImageListAdapter imageListAdapter = new ImageListAdapter(mContext, imageWords);
                rvImage.setAdapter(imageListAdapter);
            } else {
                rvImage.setVisibility(View.GONE);
            }
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
