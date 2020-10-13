package com.example.dictdev.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.dictdev.db.dao.ContentWordDao;
import com.example.dictdev.db.dao.FavoriteWordDao;
import com.example.dictdev.db.dao.SearchedWordDao;
import com.example.dictdev.db.dao.TitleWordDao;
import com.example.dictdev.db.dao.WordNoteDao;
import com.example.dictdev.db.model.Converters;
import com.example.dictdev.db.model.entity.ContentWord;
import com.example.dictdev.db.model.entity.FavoriteWord;
import com.example.dictdev.db.model.entity.SearchedWord;
import com.example.dictdev.db.model.entity.TitleWord;
import com.example.dictdev.db.model.entity.WordNote;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

@Database(entities = {TitleWord.class, ContentWord.class, SearchedWord.class, FavoriteWord.class, WordNote.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MyDatabase extends RoomDatabase {
    public abstract TitleWordDao titleWordDao();
    public abstract ContentWordDao contentWordDao();
    public abstract SearchedWordDao searchedWordDao();
    public abstract FavoriteWordDao favoriteWordDao();
    public abstract WordNoteDao wordNoteDao();

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
            ArrayList<TitleWord> titleWords = gson.fromJson(loadJSONFromAsset("TitleWord.json"), new TypeToken<ArrayList<TitleWord>>() {}.getType());
            ArrayList<ContentWord> contentWords = gson.fromJson(loadJSONFromAsset("ContentWord.json"), new TypeToken<ArrayList<ContentWord>>() {}.getType());

            mTitleWordDao.insertAll(titleWords);
            mContentWordDao.insertAll(contentWords);

            return null;
        }

        public String loadJSONFromAsset(String path) {
            String json = null;
            try {
                InputStream is = weakContext.get().getAssets().open(path);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }
    }
}
