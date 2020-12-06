package com.example.android.lifecycleweather;

import android.arch.lifecycle.ViewModel;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.Button;
import java.io.IOException;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.android.lifecycleweather.data.WeatherPreferences;
import com.example.android.lifecycleweather.utils.OpenWeatherMapUtils;
import com.example.android.lifecycleweather.utils.NetworkUtils;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements ForecastAdapter.OnForecastItemClickListener, LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mForecastLocationTV;
    private RecyclerView mForecastItemsRV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;
    private ForecastAdapter mForecastAdapter;

    private ViewModel mForecastViewModel;

    private static final String FORECAST_URL_KEY = "forecastURL";
    private static final int FORECAST_LOADER_ID = 0;

    private SharedPreferences preferences;
    private String location;
    private String unit;

    SharedPreferences.OnSharedPreferenceChangeListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Remove shadow under action bar.
        getSupportActionBar().setElevation(0);

        mForecastLocationTV = findViewById(R.id.tv_forecast_location);
        mForecastLocationTV.setText(WeatherPreferences.getDefaultForecastLocation());

        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = findViewById(R.id.tv_loading_error_message);
        mForecastItemsRV = findViewById(R.id.rv_forecast_items);

        mForecastAdapter = new ForecastAdapter(this);
        mForecastItemsRV.setAdapter(mForecastAdapter);
        mForecastItemsRV.setLayoutManager(new LinearLayoutManager(this));
        mForecastItemsRV.setHasFixedSize(true);

        getSupportLoaderManager().initLoader(FORECAST_LOADER_ID, null, this);

        //mForecastViewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                loadForecast();
            }
        };

        preferences.registerOnSharedPreferenceChangeListener(mListener);

        loadForecast();

    }

    @Override
    public void onForecastItemClick(OpenWeatherMapUtils.ForecastItem forecastItem) {
        Intent intent = new Intent(this, ForecastItemDetailActivity.class);
        intent.putExtra(OpenWeatherMapUtils.EXTRA_FORECAST_ITEM, forecastItem);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_location:
                showForecastLocation();
                return true;
            case R.id.action_settings:
                Intent intent =  new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadForecast() {

        location = preferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));

        unit = preferences.getString(getString(R.string.pref_unit_key), getString(R.string.pref_unit_default));

        Log.d(TAG, "got location: " + location);
        Log.d(TAG, "got unit: " + unit);

        WeatherPreferences.changeForecastLocation(location);
        WeatherPreferences.changeTemperatureUnits(unit);

        String openWeatherMapForecastURL = OpenWeatherMapUtils.buildForecastURL(
                    WeatherPreferences.getForecastLocation(),
                    WeatherPreferences.getTemperatureUnits()
        );

        mForecastLocationTV.setText(WeatherPreferences.getForecastLocation());

        Log.d(TAG, "got forecast url: " + openWeatherMapForecastURL);
     //   new OpenWeatherMapForecastTask().execute(openWeatherMapForecastURL);

        Bundle args = new Bundle();
        args.putString(FORECAST_URL_KEY, openWeatherMapForecastURL);
        mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        getSupportLoaderManager().restartLoader(FORECAST_LOADER_ID, args, this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        String url = null;
        if (bundle != null) {
            url = bundle.getString(FORECAST_URL_KEY);
        }
        return new ForecastLoader(this, url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String forecastJSON) {
        Log.d(TAG, "loader finished loading");
        mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
        if (forecastJSON != null) {
            mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
            mForecastItemsRV.setVisibility(View.VISIBLE);
            ArrayList<OpenWeatherMapUtils.ForecastItem> forecastItems = OpenWeatherMapUtils.parseForecastJSON(forecastJSON);
            mForecastAdapter.updateForecastItems(forecastItems);
        } else {
            mForecastItemsRV.setVisibility(View.INVISIBLE);
            mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // Nothing to do here...
    }

    public void showForecastLocation() {
        Uri geoUri = Uri.parse("geo:0,0").buildUpon()
                .appendQueryParameter("q", WeatherPreferences.getForecastLocation())
                .build();
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }



/**
    class OpenWeatherMapForecastTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String openWeatherMapURL = params[0];
            String forecastJSON = null;
            try {
                forecastJSON = NetworkUtils.doHTTPGet(openWeatherMapURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return forecastJSON;
        }

        @Override
        protected void onPostExecute(String forecastJSON) {
            mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
            if (forecastJSON != null) {
                mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
                mForecastItemsRV.setVisibility(View.VISIBLE);
                ArrayList<OpenWeatherMapUtils.ForecastItem> forecastItems = OpenWeatherMapUtils.parseForecastJSON(forecastJSON);
                mForecastAdapter.updateForecastItems(forecastItems);
            } else {
                mForecastItemsRV.setVisibility(View.INVISIBLE);
                mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
            }
        }
    }
    **/
}
