package com.example.yeongaelee.final_project.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class SaveRepository {
    private DietDao mDietDao;

    public SaveRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mDietDao = db.dietDao();
    }

    public void insertSave(SaveItem saveItem) {
        new InsertAsyncTask(mDietDao).execute(saveItem);
    }

    public void deleteSave(SaveItem saveItem) {
        new DeleteAsyncTask(mDietDao).execute(saveItem);
    }

    public LiveData<List<SaveItem>> getAllSaves() {
        return mDietDao.getAllSaves();
    }

    public LiveData<SaveItem> getSaveByDate(String date) {
        return mDietDao.getSaveByDate(date);
    }

    private static class InsertAsyncTask extends AsyncTask<SaveItem, Void, Void> {
        private DietDao mAsyncTaskDao;
        InsertAsyncTask(DietDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(SaveItem... saveItems) {
            mAsyncTaskDao.insert(saveItems[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<SaveItem, Void, Void> {
        private DietDao mAsyncTaskDao;
        DeleteAsyncTask(DietDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(SaveItem... saveItems) {
            mAsyncTaskDao.delete(saveItems[0]);
            return null;
        }
    }
}
