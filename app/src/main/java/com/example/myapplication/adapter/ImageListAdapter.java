package com.example.myapplication.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.model.ImageWord;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import lombok.SneakyThrows;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder>{
    private ArrayList<ImageWord> mImageWords;
    private Context mContext;

    public ImageListAdapter(Context mContext, ArrayList<ImageWord> mImageWords) {
        this.mContext = mContext;
        this.mImageWords = mImageWords;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageListAdapter.ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.image_item, parent, false));
    }

    @SneakyThrows
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageWord imageWord = mImageWords.get(position);

        holder.bindTo(imageWord);
    }

    @Override
    public int getItemCount() {
        return mImageWords.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private AssetManager assetManager = mContext.getAssets();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }

        void bindTo(ImageWord imageWord) throws IOException {
            String imageName = imageWord.getImageName();

            InputStream is = assetManager.open("image/" + imageName);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            imageView.setImageBitmap(bitmap);
        }
    }
}
