package com.example.yeongaelee.final_project.data;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class DietItem implements Serializable {
    @NonNull
    public String name;
    @NonNull
    public int calorie;
}
