package com.samsung.architecturecompcodelab.persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.samsung.architecturecompcodelab.AppExecutors;
import com.samsung.architecturecompcodelab.persistence.daos.WeatherDao;
import com.samsung.architecturecompcodelab.persistence.models.WeatherEntry;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by sidia on 26/01/18.
 */

/**
 * {@link AppDatabase} database for the application
 */

@Database(
        entities = {
                WeatherEntry.class
        },
        version = 1
)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String TAG = "AppDatabase";

    // TODO
    // Search why it can be volatile.
    private static AppDatabase sInstance;

    // TODO
    // Consider using a LOCK object instead of AppDatabase.class to get instance
    // private static final Object LOCK = new Object();

    private static final String DATABASE_NAME = "weather-db";

    public abstract WeatherDao weatherDao();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {

        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                }
            }
        }

        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context appContext, final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addCallback(new Callback() {
                    /**
                     * Called when the database is created for the first time. This is called after all the
                     * tables are created.
                     *
                     * @param db The database.
                     */
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        executors.getDiskIOExecutor().execute(new Runnable() {
                            @Override
                            public void run() {

                                AppDatabase appDatabase = AppDatabase.getInstance(appContext, executors);

                                List<WeatherEntry> weathers = DataGenerator.generateWeathers();
                                insertAll(appDatabase, weathers);

                                // TODO check why it is needed this variable
                                // notify that the database was created and it's ready to be used
                                // database.setDatabaseCreated();
                            }
                        });
                    }
                }).build();
    }

    private static void insertAll(final AppDatabase appDatabase, final List<WeatherEntry> weathers) {

        appDatabase.runInTransaction(new Runnable() {
            @Override
            public void run() {

                Log.d(TAG, "insertAll runInTransaction");

                appDatabase.weatherDao().insertAll(weathers);
            }
        });
    }
}
