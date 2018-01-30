package com.samsung.architecturecompcodelab.persistence.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.samsung.architecturecompcodelab.persistence.models.WeatherEntry;

import java.util.Date;
import java.util.List;

/**
 * Created by sidia on 26/01/18.
 */

/**
 * DAO which provides api for all data operations
 * */

@Dao
public interface WeatherDao {

    /**
     *
     * Insert a list of {@link WeatherEntry} into weather table.
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WeatherEntry> weatherEntries);

    /**
     *
     * Gets the weather of a single day
     *
     *  @param date The date you want the weather for
     *  @return weather for a single day
     * */
    @Query("SELECT * FROM weather WHERE date = :date")
    LiveData<WeatherEntry> getWeatherByDate(Date date);

    /**
     *
     * Gets the weather of a id
     *
     *  @param weatherId The id you want the weather for
     *  @return weather
     * */
    @Query("SELECT * FROM weather WHERE id = :weatherId")
    LiveData<WeatherEntry> loadWeather(int weatherId);

    /**
     *  Get all weathers from a period of time for a location
     * */
    @Query("SELECT * FROM weather ORDER BY date")
    LiveData<List<WeatherEntry>> loadWeathers();
}
