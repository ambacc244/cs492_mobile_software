package com.example.android.connectedweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;

public class JSONWeatherReader {

    public static String[] readWeather(JSONObject data) {

        String[] forecastData = new String[5];

        String date = null;
        double tempD;
        int temp;
        String mainWeather = null;
        String line = null;

        try {
            JSONArray dataArray = data.getJSONArray("list");

            for(int i=0; i<5; i++){

                JSONObject singleData = dataArray.getJSONObject(i);

                JSONObject main = singleData.getJSONObject("main");

                JSONArray weatherArray = singleData.getJSONArray("weather");
                JSONObject weather = weatherArray.getJSONObject(0);

                date = singleData.getString("dt_txt");
                tempD = main.getDouble("temp");
                mainWeather = weather.getString("main");

                tempD = tempD*9/5-459.67;
                temp = (int) tempD;

            //    Log.d("Line", date);
            //    Log.d("Line", "Value: " + temp);
            //    Log.d("Line", mainWeather);

                line = date + " - " + mainWeather +  " - " + temp + "F";
                forecastData[i] = line;
            //    Log.d("Line1", forecastData[i]);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return forecastData;
    }

    public static String[] readWeatherDetail(JSONObject data) {

        String[] forecastDescriptionData = new String[5];

        String date = null;
        double minTempD;
        double maxTempD;
        int minTemp;
        int maxTemp;

        String weatherDescription = null;
        String line = null;

        try {
            JSONArray dataArray = data.getJSONArray("list");

            for(int i=0; i<5; i++){

                JSONObject singleData = dataArray.getJSONObject(i);

                JSONObject main = singleData.getJSONObject("main");

                JSONArray weatherArray = singleData.getJSONArray("weather");
                JSONObject weather = weatherArray.getJSONObject(0);

                date = singleData.getString("dt_txt");
                minTempD = main.getDouble("temp_min");
                maxTempD = main.getDouble("temp_max");

                weatherDescription = weather.getString("description");

                minTempD = minTempD*9/5-459.67;
                maxTempD = maxTempD*9/5-459.67;

                minTemp = (int) minTempD;
                maxTemp = (int) maxTempD;

             //   Log.d("Line", date);
             //   Log.d("Line", "Value: " + minTemp);
             //   Log.d("Line", "Value: " + maxTemp);
             //   Log.d("Line", weatherDescription);

                line = date + "\n" + "weather: " + weatherDescription +  "\n" + "minimum temperature: " + minTemp + "F\n"+ "maximum temperature: " + maxTemp + "F\n";
                forecastDescriptionData[i] = line;

             //   Log.d("Line1", forecastDescriptionData[i]);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return forecastDescriptionData;
    }

    public static String[] readWeatherIcon(JSONObject data) {

        String[] forecastIcon = new String[5];

        String icon;

        String weatherDescription = null;
        String line = null;

        try {
            JSONArray dataArray = data.getJSONArray("list");

            for(int i=0; i<5; i++){

                String url = "http://openweathermap.org/img/w/";

                JSONObject singleData = dataArray.getJSONObject(i);

                JSONArray weatherArray = singleData.getJSONArray("weather");
                JSONObject weather = weatherArray.getJSONObject(0);

                icon = weather.getString("icon");

                url = url + icon + ".png";

                 //  Log.d("Line", icon);
                 //  Log.d("Line", url);

                forecastIcon[i] = url;

                 //  Log.d("Line1", forecastIcon[i]);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return forecastIcon;
    }
}