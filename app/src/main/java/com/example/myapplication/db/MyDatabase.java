package com.example.myapplication.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.db.dao.ContentWordDao;
import com.example.myapplication.db.dao.FavoriteWordDao;
import com.example.myapplication.db.dao.SearchedWordDao;
import com.example.myapplication.db.dao.TitleWordDao;
import com.example.myapplication.db.data.ContentWordData;
import com.example.myapplication.db.data.TitleWordData;
import com.example.myapplication.db.model.ContentWord;
import com.example.myapplication.db.model.FavoriteWord;
import com.example.myapplication.db.model.SearchedWord;
import com.example.myapplication.db.model.TitleWord;

import java.lang.ref.WeakReference;

@Database(entities = {TitleWord.class, ContentWord.class, SearchedWord.class, FavoriteWord.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract TitleWordDao titleWordDao();
    public abstract ContentWordDao contentWordDao();
    public abstract SearchedWordDao searchedWordDao();
    public abstract FavoriteWordDao favoriteWordDao();
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

            mTitleWordDao.insertAll(TitleWordData.getData());
            mContentWordDao.insertAll(ContentWordData.getData());

            return null;
        }

    }
}