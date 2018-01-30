package com.samsung.architecturecompcodelab.persistence;

import com.samsung.architecturecompcodelab.persistence.models.WeatherEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sidia on 30/01/18.
 */

public class DataGenerator {

    private static final double[] MIN = {21, 22, 23, 24, 25};
    private static final double[] MAX = {35, 36, 37, 38, 39};
    private static final double[] HUMIDITY = {1.3, 3.6, 3.7, 3.8, 3.9};
    private static final double[] PRESSURE = {5.1, 5.2, 5.3, 5.4, 5.5};
    private static final double[] WIND = {2.1, 2.2, 2.3, 2.4, 2.5};
    private static final double[] DEGREE = {32, 33, 34, 30, 29};

    public static List<WeatherEntry> generateWeathers() {

        List<WeatherEntry> listWeather = new ArrayList<>();
        Date date = new Date(System.currentTimeMillis());

        for (int i = 0; i < DEGREE.length; i++) {

            WeatherEntry weatherEntry
                    = new WeatherEntry(0, date,
                    MIN[i], MAX[i], HUMIDITY[i], PRESSURE[i], WIND[i], DEGREE[i]);
            listWeather.add(weatherEntry);
        }

        return listWeather;
    }
}
