package com.example.android.sqliteweather.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


import java.io.Serializable;

@Entity(tableName = "location")
public class Location implements Serializable{
    @NonNull
    @PrimaryKey
    public String location;

  //  public int stargazers_count;
}
