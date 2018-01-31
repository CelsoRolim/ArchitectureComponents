package com.samsung.architecturecompcodelab.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.samsung.architecturecompcodelab.persistence.entities.WeatherEntry;

/**
 * Created by sidia on 26/01/18.
 */

public class WeatherDetailViewModel extends AndroidViewModel {

    private final LiveData<WeatherEntry> mObservableWeather;

    public WeatherDetailViewModel(@NonNull Application application) {
        super(application);
        mObservableWeather = null;
    }

    public LiveData<WeatherEntry> getWeather() {
        return mObservableWeather;
    }
}
