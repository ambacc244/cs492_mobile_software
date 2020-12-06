package com.example.android.sqliteweather.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class LocationRepository {
    private LocationDao mLocationDao;

    public LocationRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mLocationDao = db.locationDao();
    }

    public void insertLocation(Location location) {
        new InsertAsyncTask(mLocationDao).execute(location);
    }

    public void deleteLocation(Location location) {
        new DeleteAsyncTask(mLocationDao).execute(location);
    }

    public LiveData<List<Location>> getAllLocations() {
        return mLocationDao.getAllLocations();
    }

    public LiveData<Location> getLocationByName(String location) {
        return mLocationDao.getLocationByName(location);
    }

    private static class InsertAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao mAsyncTaskDao;
        InsertAsyncTask(LocationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Location... locations) {
            Log.d("TAGTAG", locations[0].location);
            mAsyncTaskDao.insert(locations[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao mAsyncTaskDao;
        DeleteAsyncTask(LocationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Location... locations) {
            Log.d("TAGTAG", locations[0].location);
            mAsyncTaskDao.delete(locations[0]);
            return null;
        }
    }
}
