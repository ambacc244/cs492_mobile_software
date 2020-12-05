package com.example.android.basicweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements BasicWeatherAdapter.OnBasicWeatherMessageChangeListener{

    private String[] basicweathers = {
            "Thu Jan 24 - AM Fog/PM Clouds - 49F",
            "Fri Jan 25 - AM Fog/PM Sun - 50F",
            "Sat Jan 26 - AM Fog/PM Clouds - 53F",
            "Sun Jan 27 - AM Fog/PM Sun - 54F",
            "Mon Jan 28 - Sunny - 52F",
            "Tue Jan 29 - Mostly Sunny - 49F",
            "Wed Jan 30 - Fog Late - 52F",
            "Thu Jan 31 - AM Fog/PM Clouds - 53F",
            "Fri Fed 1 - Showers - 52F",
            "Sat Fed 2 - Showers - 53F",
    };

    private String[] weatherdescription = {
            "AM Fog and PM Clouds, with a high of 49F and a low of 34F",
            "AM Fog and PM Sun, with a high of 50F and a low of 34F",
            "AM Fog and PM Clouds, with a high of 53F and a low of 34F",
            "AM Fog/PM Sun, with a high of 54F and a low of 35F",
            "Sunny, with a high of 52F and a low of 28F",
            "Mostly Sunny, with a high of 49F and a low of 32F",
            "Fog Late, with a high of 52F and a low of 38F",
            "AM Fog and PM Clouds, with a high of 53F and a low of 39F",
            "Showers, with a high of 52F and a low of 41F",
            "Showers, with a high of 53F and a low of 40F",
    };

    private RecyclerView mBasicWeatherListRV;
    private BasicWeatherAdapter mBasicWeatherAdapter;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBasicWeatherListRV = findViewById(R.id.rv_basicweather_list);

        mBasicWeatherListRV.setLayoutManager(new LinearLayoutManager(this));
        mBasicWeatherListRV.setHasFixedSize(true);

        mBasicWeatherAdapter = new BasicWeatherAdapter(this);
        mBasicWeatherListRV.setAdapter(mBasicWeatherAdapter);

        mBasicWeatherListRV.setItemAnimator(new DefaultItemAnimator());

        mToast = null;

        for(int i = 0; i < 10; i++){
            mBasicWeatherAdapter.addBasicWeather(basicweathers[i]);
        }
    }

    @Override
    public void onBasicWeatherMessageChanged(int i){
        if(mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(this, weatherdescription[i], 8000);
        mToast.show();
    }
}
