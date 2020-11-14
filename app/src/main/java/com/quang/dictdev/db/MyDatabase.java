package com.quang.dictdev.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.quang.dictdev.db.dao.WordDao;
import com.quang.dictdev.db.model.converter.WordContentConverter;
import com.quang.dictdev.db.model.converter.WordImageConverter;
import com.quang.dictdev.db.model.entity.Word;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
@TypeConverters({WordContentConverter.class, WordImageConverter.class})
public abstract class MyDatabase extends RoomDatabase {
    public abstract WordDao wordDao();

    private static MyDatabase INSTANCE;

    public static MyDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "my_database")
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
        private final WordDao mWordDao;
        private final WeakReference<Context> weakContext;

        PopulateDbAsync(MyDatabase db, Context context) {
            mWordDao = db.wordDao();
            weakContext = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(final Void... params) {
            Gson gson = new Gson();
            ArrayList<Word> words = gson.fromJson(loadJSONFromAsset("Word.json"), new TypeToken<ArrayList<Word>>() {}.getType());
            mWordDao.insertAll(words);

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
