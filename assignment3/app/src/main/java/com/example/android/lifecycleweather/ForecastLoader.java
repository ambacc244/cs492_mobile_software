package com.example.android.lifecycleweather;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.lifecycleweather.utils.NetworkUtils;

import java.io.IOException;

public class ForecastLoader extends AsyncTaskLoader<String> {

    private final static String TAG = ForecastLoader.class.getSimpleName();

    private String mResultsJSON;
    private String mURL;

    ForecastLoader(Context context, String url) {
        super(context);
        mURL = url;
    }

    @Override
    protected void onStartLoading() {
        if (mURL != null) {
            if (mResultsJSON != null) {
                Log.d(TAG, "loader returning cached results");
                deliverResult(mResultsJSON);
            } else {
                forceLoad();
            }
        }
    }

    @Nullable
    @Override
    public String loadInBackground() {
        if (mURL != null) {
            Log.d(TAG, "loading results from GitHub with URL: " + mURL);
            String results = null;
            try {
                results = NetworkUtils.doHTTPGet(mURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        } else {
            return null;
        }
    }

    @Override
    public void deliverResult(@Nullable String data) {
        mResultsJSON = data;
        super.deliverResult(data);
    }

}
