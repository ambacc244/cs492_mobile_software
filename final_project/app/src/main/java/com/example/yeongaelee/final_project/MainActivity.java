package com.example.yeongaelee.final_project;


import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Calendar;
import com.example.yeongaelee.final_project.data.DietItem;
import com.example.yeongaelee.final_project.data.SaveItem;
import com.example.yeongaelee.final_project.utils.DietUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.Locale;


import org.json.JSONArray;


public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;

    private static ArrayList<DietItem> dietItems;
    private  ArrayList<String> urlArray;
    private final int SIZE = 10;
    private int index = 0;

    private Button calculate_button;
    private Button save_button;

    private int[] editModelArrayList;
    private int total;
    private int save;

    private  TextView totalCalorieView;
    private EditText searchText;

    public SaveViewModel mSaveViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dietItems = new ArrayList<DietItem>();
        urlArray = DietUtils.buildDietURLs();

        recyclerView= findViewById(R.id.rv_diet_list);

        calculate_button = (Button) findViewById(R.id.cal_button_id);
        save_button = (Button) findViewById(R.id.save_button_id);

        editModelArrayList = new int[SIZE];

        for(int i = 0; i<SIZE; i++)
            editModelArrayList[i] = 0;

        customAdapter = new CustomAdapter(editModelArrayList);
        recyclerView.setAdapter(customAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        totalCalorieView = (TextView) findViewById(R.id.calorie_text);

        searchText = findViewById(R.id.ed_search);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        for(int i = 0 ; i < SIZE; i++) {
            new callData().execute();
        }

        mSaveViewModel = ViewModelProviders.of(this).get(SaveViewModel.class);

        calculate_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                editModelArrayList = customAdapter.getValue();
                String result;
                for(int i=0; i< SIZE; i++){
                    Log.d("TAG", "Outside: " + i + " = " + editModelArrayList[i]);
                }

                total = calculator();
            //    Log.d("TAG", "Total: " + total);
                result = Integer.toString(total) + " cal";

                totalCalorieView.setText(result);
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] strings = totalCalorieView.getText().toString().split(" cal");
                save = Integer.parseInt(strings[0]);
                Date currentTime = Calendar.getInstance().getTime();

            //    Log.d("TAG", "Save: " + save);
            //    Log.d("TAG", "Time: " + currentTime.toString());

                SaveItem saveItem = new SaveItem();
                saveItem.date = currentTime.toString();
                saveItem.calorie = save;

                mSaveViewModel.insertSaveItem(saveItem);
            }
        });


        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = searchText.getText().toString().toLowerCase(Locale.getDefault());
                customAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });


    }

    public int calculator(){
        int total_cal = 0;

        for(int i = 0; i<SIZE; i++) {
            Log.d("TAG", "Calculation" + customAdapter.getDietItemCalorie(i) + "*" + editModelArrayList[i]);
            total_cal = total_cal + customAdapter.getDietItemCalorie(i) * editModelArrayList[i] / 100;
        }

        return total_cal;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_saved:
                Intent intent = new Intent(this, SaveActivity.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class callData extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject result = null;
            HttpURLConnection connection;

            try {
                URL urlOpen = new URL(urlArray.get(index));
                index = index + 1;
                connection = (HttpURLConnection) urlOpen.openConnection();
                InputStream in = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

                StringBuffer json = new StringBuffer(1000);
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    json.append(line).append("\n");
                }

                result = new JSONObject(json.toString());

                bufferedReader.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            converter(s);
        }
    }

    private void converter(JSONObject object){

        DietItem dietItem = new DietItem();
        String name;
        try {
            JSONObject report = object.getJSONObject("report");
            JSONObject food = report.getJSONObject("food");

            name = food.getString("name");
         //   Log.d("name", food.getString("name"));
            String[] strings = name.split(",");
            dietItem.name = strings[0];
            JSONArray nutrients = food.getJSONArray("nutrients");
            JSONObject nutrient = nutrients.getJSONObject(0);

            dietItem.calorie = nutrient.getInt("value");
         //   Log.d("calorie", String.valueOf(nutrient.getInt("value")));

        } catch (Exception e){
            e.printStackTrace();
        }

        //Log.d("name2", dietItem.name);
        //Log.d("calorie2", String.valueOf(dietItem.calorie));

        customAdapter.addItem(dietItem);

        //Log.d("name3", dietItems.get(0).name);
        //Log.d("calorie3", String.valueOf(dietItems.get(0).calorie));
    }
}
