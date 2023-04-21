package com.edu.androidproject.data.datasource;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserAccountEntity.class}, version = 1, exportSchema = false)
public abstract class UserAccountRoomDatabase extends RoomDatabase
{
    public abstract UserAccountDAO userAccountDAO();

    private static volatile UserAccountRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UserAccountRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserAccountRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserAccountRoomDatabase.class, "account_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
