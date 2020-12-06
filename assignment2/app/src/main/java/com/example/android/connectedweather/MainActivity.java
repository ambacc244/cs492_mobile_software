package com.example.android.connectedweather;

import android.os.AsyncTask;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import	android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements ForecastAdapter.OnForecastItemClickListener {

    private RecyclerView mForecastListRV;
    private ForecastAdapter mForecastAdapter;
    private ProgressBar mLoadingPB;

    private String[] forecastData;
    private String[] forecastDetailData;
    private String[] forecastIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingPB = findViewById(R.id.pb_loading);
        mForecastListRV = (RecyclerView)findViewById(R.id.rv_forecast_list);

        mForecastListRV.setLayoutManager(new LinearLayoutManager(this));
        mForecastListRV.setHasFixedSize(true);

        mForecastAdapter = new ForecastAdapter(this);
        mForecastListRV.setAdapter(mForecastAdapter);

        new ForcastTask().execute();
    }

    public void onForecastItemClick(String detailedForecast) {

        Intent intent = new Intent(this, ForecastDetailActivity.class);
        intent.putExtra("content", detailedForecast);
        startActivity(intent);
    }

    class ForcastTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingPB.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject result = null;
            HttpURLConnection connection = null;

            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=Corvallis,US&APPID=e04e8c889d018498a5fda45e08414de5");
                connection = (HttpURLConnection) url.openConnection();

                InputStream in = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

                StringBuffer json = new StringBuffer(1000);
                String line = "";

                while ((line = bufferedReader.readLine()) != null){
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

            String data = null;

            if (s != null) {
                mLoadingPB.setVisibility(View.INVISIBLE);
                forecastData = JSONWeatherReader.readWeather(s);
                forecastDetailData = JSONWeatherReader.readWeatherDetail(s);
                forecastIcon = JSONWeatherReader.readWeatherIcon(s);

             //   Log.d("Line1", forecastData[0]);
             //   Log.d("Line1", forecastDetailData[0]);

                ArrayList<String> forecastData = new ArrayList<>(Arrays.asList(MainActivity.this.forecastData));
                forecastData.addAll(Arrays.asList(MainActivity.this.forecastData));
                forecastData.addAll(Arrays.asList(MainActivity.this.forecastData));
                forecastData.addAll(Arrays.asList(MainActivity.this.forecastData));
                ArrayList<String> detailedForecastData = new ArrayList<>(Arrays.asList(forecastDetailData));
                detailedForecastData.addAll(Arrays.asList(forecastDetailData));
                detailedForecastData.addAll(Arrays.asList(forecastDetailData));
                detailedForecastData.addAll(Arrays.asList(forecastDetailData));
                ArrayList<String> forecastIcon = new ArrayList<>(Arrays.asList(MainActivity.this.forecastIcon));
                forecastIcon.addAll(Arrays.asList(MainActivity.this.forecastIcon));
                forecastIcon.addAll(Arrays.asList(MainActivity.this.forecastIcon));
                forecastIcon.addAll(Arrays.asList(MainActivity.this.forecastIcon));
                mForecastAdapter.updateForecastData(
                        forecastData,
                        detailedForecastData,
                        forecastIcon
                );
            }
            else{
                mLoadingPB.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.forecast_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map:
                map();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void map() {
        Uri URL = Uri.parse("geo:44.5646,-123.2621");
  //      Uri URL =  Uri.parse("https://tile.openweathermap.org/map/temp_new/10/44.5646/-123.2621.png?appid=e04e8c889d018498a5fda45e08414de5");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, URL);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }

    }
}
