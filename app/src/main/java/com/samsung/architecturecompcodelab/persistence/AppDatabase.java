package com.samsung.architecturecompcodelab.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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
import com.samsung.architecturecompcodelab.persistence.entities.WeatherEntry;

import java.util.List;

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

    public static final String TAG = AppDatabase.class.getSimpleName();

    // TODO
    // Search why it can be volatile.
    private static AppDatabase sInstance;

    // TODO
    // Consider using a LOCK object instead of AppDatabase.class to get instance
    // private static final Object LOCK = new Object();

    private static final String DATABASE_NAME = "weather-db";

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public abstract WeatherDao weatherDao();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {

        Log.d(TAG, "Codelab AppDatabase getInstance");

        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }

        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context appContext, final AppExecutors executors) {

        Log.d(TAG, "Codelab AppDatabase buildDatabase");

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

                                // Add a delay to simulate a long-running operation
                                addDelay();

                                AppDatabase appDatabase = AppDatabase.getInstance(appContext, executors);

                                List<WeatherEntry> weathers = DataGenerator.generateWeathers();

                                Log.d(TAG, "Codelab AppDatabase insertAll - begin");

                                insertAll(appDatabase, weathers);

                                // notify that the database was created and it's ready to be used
                                appDatabase.setDatabaseCreated();
                            }
                        });
                    }
                }).build();
    }

    private static void insertAll(final AppDatabase appDatabase, final List<WeatherEntry> weathers) {

        appDatabase.runInTransaction(new Runnable() {
            @Override
            public void run() {

                Log.d(TAG, "Codelab insertAll runInTransaction");

                appDatabase.weatherDao().insertAll(weathers);

                Log.d(TAG, "Codelab AppDatabase insertAll - end");
            }
        });
    }

    /**
     * Only for testing purpose
     */
    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    public LiveData<Boolean> isDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    /**
     * Check whether the database already exists and expose it via {@link #isDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }
}
