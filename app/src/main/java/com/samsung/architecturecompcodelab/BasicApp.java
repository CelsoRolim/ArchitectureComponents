package com.samsung.architecturecompcodelab;

import android.app.Application;

import com.samsung.architecturecompcodelab.persistence.AppDatabase;

/**
 * Created by sidia on 29/01/18.
 */

public class BasicApp extends Application {

    private AppExecutors mExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mExecutors = new AppExecutors();
    }

    public AppDatabase getAppDatabase() {

        // TODO
        // Add executors parameter
        return AppDatabase.getInstance(this, mExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getAppDatabase());
    }
}
