package com.example.myapplication.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.R;
import com.example.myapplication.db.dao.ContentWordDao;
import com.example.myapplication.db.dao.SearchedWordDao;
import com.example.myapplication.db.dao.TitleWordDao;
import com.example.myapplication.db.model.ContentWord;
import com.example.myapplication.db.model.SearchedWord;
import com.example.myapplication.db.model.TitleWord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

@Database(entities = {TitleWord.class, ContentWord.class, SearchedWord.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract TitleWordDao titleWordDao();
    public abstract ContentWordDao contentWordDao();
    public abstract SearchedWordDao searchedWordDao();
    private static MyDatabase INSTANCE;

    public static MyDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    new PopulateDbAsync(INSTANCE, context).execute();
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }



    /**
     * Populate the database in the background.
     **/
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TitleWordDao mTitleWordDao;
        private final ContentWordDao mContentWordDao;
        private final WeakReference<Context> weakContext;

        PopulateDbAsync(MyDatabase db, Context context) {
            mTitleWordDao = db.titleWordDao();
            mContentWordDao = db.contentWordDao();
            weakContext = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mTitleWordDao.deleteAll();
            mContentWordDao.deleteAll();

            Gson gson = new Gson();
            ArrayList<TitleWord> titleWords = gson.fromJson(readTextFile(weakContext.get().getResources().openRawResource(R.raw.title_word)), new TypeToken<ArrayList<TitleWord>>(){}.getType());
            ArrayList<ContentWord> contentWords = gson.fromJson(readTextFile(weakContext.get().getResources().openRawResource(R.raw.content_word)), new TypeToken<ArrayList<ContentWord>>(){}.getType());


            mTitleWordDao.insertAll(titleWords);
            mContentWordDao.insertAll(contentWords);

            return null;
        }

        public static String readTextFile(InputStream inputStream) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte buf[] = new byte[1024];
            int len;
            try {
                while ((len = inputStream.read(buf)) != -1) {
                    outputStream.write(buf, 0, len);
                }
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {

            }
            return outputStream.toString();
        }
    }
}
