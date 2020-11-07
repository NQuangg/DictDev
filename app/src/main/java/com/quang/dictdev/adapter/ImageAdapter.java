package com.quang.dictdev.adapter;

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

import com.bumptech.glide.Glide;
import com.quang.dictdev.R;
import com.quang.dictdev.db.model.WordImage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import lombok.SneakyThrows;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
    private ArrayList<WordImage> mWordImages;
    private Context mContext;

    public ImageAdapter(Context mContext, ArrayList<WordImage> mImages) {
        this.mContext = mContext;
        this.mWordImages = mImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageAdapter.ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.item_image, parent, false));
    }

    @SneakyThrows
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WordImage imageWord = mWordImages.get(position);

        holder.bindTo(imageWord);
    }

    @Override
    public int getItemCount() {
        return mWordImages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private AssetManager assetManager = mContext.getAssets();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }

        void bindTo(WordImage imageWord) throws IOException {
            String imageName = imageWord.getImageName();

            InputStream is = assetManager.open("image/" + imageName);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();

            Glide.with(mContext).asBitmap().load(bitmap).into(imageView);

        }
    }
}
