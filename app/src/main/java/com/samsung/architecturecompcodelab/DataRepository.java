package com.samsung.architecturecompcodelab;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.samsung.architecturecompcodelab.persistence.AppDatabase;
import com.samsung.architecturecompcodelab.persistence.entities.WeatherEntry;

import java.util.Date;
import java.util.List;

/**
 * Created by sidia on 26/01/18.
 */

/**
 * {@link DataRepository} is a way of organizing data in the application.
 * It provides a api for the rest of the app.
 */

public class DataRepository {

    private static final String TAG = DataRepository.class.getSimpleName();

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;

    // TODO
    // check why it is used MediatorLiveData instead of a simple LiveData
    // Maybe it is because the Room will dispatch an event when the database is changed.
    // Instead of the application trigger the event, the database will do that.
    private MediatorLiveData<List<WeatherEntry>> mObservableWeathers;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;

        mObservableWeathers = new MediatorLiveData<>();
        mObservableWeathers.addSource(mDatabase.weatherDao().loadWeathers(), new Observer<List<WeatherEntry>>() {
            @Override
            public void onChanged(@Nullable List<WeatherEntry> weatherEntries) {

                Log.d(TAG, "Codelab DataRepository mObservableWeathers.onChanged");
                Log.d(TAG, "Codelab weatherEntries: " + weatherEntries);

                if (mDatabase.isDatabaseCreated().getValue() != null) {
                    Log.d(TAG, "Codelab mDatabase.isDatabaseCreated created");
                    mObservableWeathers.postValue(weatherEntries);
                } else {
                    Log.d(TAG, "Codelab mDatabase.isDatabaseCreated is not created");
                }
            }
        });
    }

    public static DataRepository getInstance(final AppDatabase database) {

        Log.d(TAG, "Codelab DataRepository getInstance");

        if (sInstance == null) {

            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }

        return sInstance;
    }

    public LiveData<WeatherEntry> getWeather(final Date date) {
        return mDatabase.weatherDao().getWeatherByDate(date);
    }

    public LiveData<WeatherEntry> getWeather(final int weatherId) {
        return mDatabase.weatherDao().loadWeather(weatherId);
    }

    public LiveData<List<WeatherEntry>> getWeathers() {
        return mObservableWeathers;
    }
}
