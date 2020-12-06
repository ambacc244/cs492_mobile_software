package com.example.yeongaelee.final_project;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.yeongaelee.final_project.data.SaveItem;
import com.example.yeongaelee.final_project.data.SaveRepository;

import java.util.List;

public class SaveViewModel extends AndroidViewModel {
    private SaveRepository mSaveRepository;

    public SaveViewModel(Application application) {
        super(application);
        mSaveRepository = new SaveRepository(application);
    }

    public void insertSaveItem(SaveItem saveItem) {
        mSaveRepository.insertSave(saveItem);
    }

    public void deleteSaveItem(SaveItem saveItem) {
        mSaveRepository.deleteSave(saveItem);
    }

    public LiveData<List<SaveItem>> getAllSaveItems() {
        return mSaveRepository.getAllSaves();
    }

    public LiveData<SaveItem> getSaveItemByName(String date) {
        return mSaveRepository.getSaveByDate(date);
    }
}