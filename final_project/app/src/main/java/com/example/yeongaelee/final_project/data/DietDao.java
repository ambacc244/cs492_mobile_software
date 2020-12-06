package com.example.yeongaelee.final_project.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DietDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(SaveItem savedItem);

    @Delete
    void delete(SaveItem savedItem);

    @Query("SELECT * FROM dietItem")
    LiveData<List<SaveItem>> getAllSaves();

    @Query("SELECT * FROM dietItem WHERE date = :date LIMIT 1")
    LiveData<SaveItem> getSaveByDate(String date);
}
