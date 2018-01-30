package com.samsung.architecturecompcodelab.persistence.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by sidia on 26/01/18.
 */

/**
 * Class to represent a single weather entry.
 * A weather forecast will be stored only for one location.
 *
 */

@Entity(tableName = "weather", indices = {@Index(value = {"date"}, unique = true)})
public class WeatherEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "weather_icon_id")
    private int mWeatherIconId;

    @ColumnInfo(name = "date")
    private Date mDate;

    @ColumnInfo(name = "min")
    private double mMin;

    @ColumnInfo(name = "max")
    private double mMax;

    @ColumnInfo(name = "humidity")
    private double mHumidity;

    @ColumnInfo(name = "pressure")
    private double mPressure;

    @ColumnInfo(name = "wind")
    private double mWind;

    @ColumnInfo(name = "degrees")
    private double mDegrees;

    /**
     * Constructor needed by Room to create weather entities
     * */
    public WeatherEntry(int id, int mWeatherIconId, Date mDate, double mMin,
                        double mMax, double mHumidity, double mPressure,
                        double mWind, double mDegrees) {
        this.id = id;
        this.mWeatherIconId = mWeatherIconId;
        this.mDate = mDate;
        this.mMin = mMin;
        this.mMax = mMax;
        this.mHumidity = mHumidity;
        this.mPressure = mPressure;
        this.mWind = mWind;
        this.mDegrees = mDegrees;
    }

    /**
     * Constructor needed to parse json objects from web server.
     * It has '@ignore' annotation due to Room cannot compile an entity
     * with two constructors
     *
     * */
    @Ignore
    public WeatherEntry(int mWeatherIconId, Date mDate, double mMin,
                        double mMax, double mHumidity, double mPressure,
                        double mWind, double mDegrees) {
        this.mWeatherIconId = mWeatherIconId;
        this.mDate = mDate;
        this.mMin = mMin;
        this.mMax = mMax;
        this.mHumidity = mHumidity;
        this.mPressure = mPressure;
        this.mWind = mWind;
        this.mDegrees = mDegrees;
    }

    public int getId() {
        return id;
    }

    public int getWeatherIconId() {
        return mWeatherIconId;
    }

    public Date getDate() {
        return mDate;
    }

    public double getMin() {
        return mMin;
    }

    public double getMax() {
        return mMax;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public double getPressure() {
        return mPressure;
    }

    public double getWind() {
        return mWind;
    }

    public double getDegrees() {
        return mDegrees;
    }
}
