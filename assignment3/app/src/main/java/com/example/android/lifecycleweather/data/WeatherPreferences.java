package com.example.android.lifecycleweather.data;

import android.util.Log;

public class WeatherPreferences {
    private static final String DEFAULT_FORECAST_LOCATION = "Corvallis,OR,US";
    private static String FORECAST_LOCATION = "";

    private static final String DEFAULT_TEMPERATURE_UNITS = "imperial";
    private static final String IMPERIAL_TEMPERATURE_UNITS = "imperial";
    private static final String METRIC_TEMPERATURE_UNITS = "metric";
    private static final String KELVIN_TEMPERATURE_UNITS = "kevin";
    private static String TEMPERATURE_UNITS = "";

    private static final String DEFAULT_TEMPERATURE_UNITS_ABBR = "F";
    private static final String IMPERIAL_TEMPERATURE_UNITS_ABBR = "F";
    private static final String METRIC_TEMPERATURE_UNITS_ABBR = "C";
    private static final String KELVIN_TEMPERATURE_UNITS_ABBR = "K";
    private static String TEMPERATURE_UNITS_ABBR = "";

    public static void changeForecastLocation(String location) {
        if(location.equals(""))
            FORECAST_LOCATION = DEFAULT_FORECAST_LOCATION;
        else
            FORECAST_LOCATION = location;
    }

    public static String getDefaultForecastLocation() { return DEFAULT_FORECAST_LOCATION; }
    public static String getForecastLocation() { return FORECAST_LOCATION; }

    public static void changeTemperatureUnits(String unit) {
        if (unit.equals("imperial")) {
            TEMPERATURE_UNITS = IMPERIAL_TEMPERATURE_UNITS;
            TEMPERATURE_UNITS_ABBR = IMPERIAL_TEMPERATURE_UNITS_ABBR;
        }
        else if (unit.equals("metric")){
            Log.d("TAG", "I am HERER HERE");
            TEMPERATURE_UNITS = METRIC_TEMPERATURE_UNITS;
            TEMPERATURE_UNITS_ABBR = METRIC_TEMPERATURE_UNITS_ABBR;
        }
        else if (unit.equals("kelvin")){
            TEMPERATURE_UNITS = KELVIN_TEMPERATURE_UNITS;
            TEMPERATURE_UNITS_ABBR = KELVIN_TEMPERATURE_UNITS_ABBR;
        }
        else {
            TEMPERATURE_UNITS = IMPERIAL_TEMPERATURE_UNITS;
            TEMPERATURE_UNITS_ABBR = IMPERIAL_TEMPERATURE_UNITS_ABBR;
        }
    }

    public static String getDefaultTemperatureUnits() { return DEFAULT_TEMPERATURE_UNITS; }
    public static String getTemperatureUnits() { return TEMPERATURE_UNITS; }

    public static String getDefaultTemperatureUnitsAbbr() { return DEFAULT_TEMPERATURE_UNITS_ABBR; }
    public static String getTemperatureUnitsAbbr(){ return TEMPERATURE_UNITS_ABBR; }

}
