package com.example.android.sqliteweather;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.sqliteweather.data.Location;
import com.example.android.sqliteweather.data.LocationRepository;

import java.util.List;

public class LocationViewModel extends AndroidViewModel {
    private LocationRepository mLocationRepository;

    public LocationViewModel(Application application) {
        super(application);
        mLocationRepository = new LocationRepository(application);
    }

    public void insertLocation(Location location) {
        mLocationRepository.insertLocation(location);
    }

    public void deleteLocation(Location location) {
        mLocationRepository.deleteLocation(location);
    }

    public LiveData<List<Location>> getAllLocations() {
        return mLocationRepository.getAllLocations();
    }

    public LiveData<Location> getLocationByName(String location) {
        return mLocationRepository.getLocationByName(location);
    }
}
