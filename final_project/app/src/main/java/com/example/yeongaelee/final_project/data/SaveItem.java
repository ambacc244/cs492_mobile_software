package com.example.yeongaelee.final_project.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;


@Entity(tableName = "dietItem")
public class SaveItem implements Serializable {
    @NonNull
    @PrimaryKey
    public String date;

    public int calorie;
}
