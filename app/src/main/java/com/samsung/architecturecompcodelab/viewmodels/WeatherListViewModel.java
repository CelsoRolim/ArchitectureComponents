package com.samsung.architecturecompcodelab.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.samsung.architecturecompcodelab.BasicApp;
import com.samsung.architecturecompcodelab.persistence.entities.WeatherEntry;

import java.util.List;

/**
 * Created by sidia on 26/01/18.
 */

public class WeatherListViewModel extends AndroidViewModel {

    public static final String TAG = "WeatherListViewModel";

    private MediatorLiveData<List<WeatherEntry>> mObservableWeathers;

    public WeatherListViewModel(@NonNull Application application) {
        super(application);

        Log.d(TAG, "Codelab WeatherListViewModel()");

        mObservableWeathers = new MediatorLiveData<>();

        // set null by default, until we get data from the database.
        mObservableWeathers.setValue(null);

        LiveData<List<WeatherEntry>> weathers = ((BasicApp) application).getRepository()
                .getWeathers();

        mObservableWeathers.addSource(weathers, new Observer<List<WeatherEntry>>() {
            @Override
            public void onChanged(@Nullable List<WeatherEntry> weatherEntries) {
                Log.d(TAG, "Codelab WeatherListViewModel.onChanged");
                mObservableWeathers.setValue(weatherEntries);
            }
        });
    }

    public LiveData<List<WeatherEntry>> getObservableWeathers() {
        return mObservableWeathers;
    }
}
