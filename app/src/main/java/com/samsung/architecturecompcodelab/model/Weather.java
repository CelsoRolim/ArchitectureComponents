package com.samsung.architecturecompcodelab.model;

import java.util.Date;

/**
 * Created by sidia on 02/02/18.
 */

public interface Weather {
    int getId();
    int getWeatherIconId();
    Date getDate();
    double getMin();
    double getMax();
    double getHumidity();
    double getPressure();
    double getWind();
    double getDegrees();
}
