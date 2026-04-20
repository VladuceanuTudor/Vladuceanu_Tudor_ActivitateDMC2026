package com.example.lab8;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TV.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class
TVDatabase extends RoomDatabase {

    private static TVDatabase instance;

    public abstract TVDao tvDao();

    public static synchronized TVDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    TVDatabase.class,
                    "tv_database"
            ).allowMainThreadQueries().build();
        }
        return instance;
    }
}
