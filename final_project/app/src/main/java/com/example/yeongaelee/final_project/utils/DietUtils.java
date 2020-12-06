package com.example.yeongaelee.final_project.utils;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;


public class DietUtils extends AppCompatActivity {

    private static final String BASE_URL = " https://api.nal.usda.gov/ndb/reports/?ndbno=";

    private static final String APPID = "&api_key=MDrUrHEli29VnIa5O5zSxSNa6EzagVbeDDoKJCNI";

    private static int[] itemIDs = {45297164, 45346596, 45136297, 45135885, 45211880, 45344614, 45339661, 45203479, 45226644, 45346458 };
    private static final int SIZE = 10;
    private static int index = 0;

    private static String buildDietURL(int itemID) {
        String url = BASE_URL + Integer.toString(itemID) + APPID;
        return url;
    }

    public static ArrayList<String> buildDietURLs(){

        ArrayList<String> urls = new ArrayList<String>();

        for(int i = 0; i < SIZE; i++){
            urls.add(buildDietURL(itemIDs[i]));
        }
        return urls;
    }
}
