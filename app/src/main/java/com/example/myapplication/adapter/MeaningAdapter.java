package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class MeaningAdapter extends RecyclerView.Adapter<MeaningAdapter.ViewHolder>{
    private ArrayList<Meaning> mMeanings;
    private Context mContext;

    public MeaningAdapter(Context mContext, ArrayList<Meaning> mMeaningData) {
        this.mMeanings = mMeaningData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.meaning_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meaning currentMeaning = mMeanings.get(position);

        holder.bindTo(currentMeaning);
    }

    @Override
    public int getItemCount() {
        return mMeanings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView typeWord;
        private TextView meaningWord;
        private TextView definitionWord;
        private TextView exampleWord;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            typeWord = itemView.findViewById(R.id.typeWord);
            meaningWord = itemView.findViewById(R.id.meaningWord);
            definitionWord = itemView.findViewById(R.id.definitionWord);
            exampleWord = itemView.findViewById(R.id.exampleWord);
        }

        void bindTo(Meaning currentMeaning){
            typeWord.setText(currentMeaning.getType());
            meaningWord.setText(currentMeaning.getMeaning());
            definitionWord.setText(currentMeaning.getDefinition());
            exampleWord.setText(currentMeaning.getExample());
        }
    }
}
