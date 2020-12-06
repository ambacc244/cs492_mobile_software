package com.example.android.sqliteweather.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import java.io.Serializable;
import java.util.Date;

/*
 * This is the class that's used to represent an individual forecast item throughout the app.
 */
public class ForecastItem implements Serializable {
    public Date dateTime;
    public String description;
    public String icon;
    public long temperature;
    public long temperatureLow;
    public long temperatureHigh;
    public long humidity;
    public long windSpeed;
    public String windDirection;
}
