package com.example.android.sqliteweather.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LocationDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Location location);
/**
    @Query("INSERT INTO location (location) VALUES (:location)")
    void insert(String location);
**/
    @Delete
    void delete(Location location);

    @Query("SELECT * FROM location")
    LiveData<List<Location>> getAllLocations();

    @Query("SELECT * FROM location WHERE location = :location LIMIT 1")
    LiveData<Location> getLocationByName(String location);
}
