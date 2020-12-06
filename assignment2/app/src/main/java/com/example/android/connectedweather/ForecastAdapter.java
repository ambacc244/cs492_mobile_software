package com.example.android.connectedweather;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.InputStream;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;

import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastItemViewHolder> {

    private ArrayList<String> mForecastData;
    private ArrayList<String> mDetailedForecastData;
    private ArrayList<String> mForecastIcon;

    private OnForecastItemClickListener mOnForecastItemClickListener;

    public ForecastAdapter(OnForecastItemClickListener onForecastItemClickListener) {
        mOnForecastItemClickListener = onForecastItemClickListener;
    }

    public void updateForecastData(ArrayList<String> forecastData, ArrayList<String> detailedForecastData, ArrayList<String> forecastIcon) {
        mForecastData = forecastData;
        mDetailedForecastData = detailedForecastData;
        mForecastIcon = forecastIcon;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mForecastData != null && mDetailedForecastData != null) {
            return Math.min(mForecastData.size(), mDetailedForecastData.size());
        } else {
            return 0;
        }
    }

    @Override
    public ForecastItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.forecast_list_item, parent, false);
        return new ForecastItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastItemViewHolder holder, int position) {
        holder.bind(mForecastData.get(position), mForecastIcon.get(position));
    }

    public interface OnForecastItemClickListener {
        void onForecastItemClick(String detailedForecast);
    }

    class ForecastItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mForecastTextView;
        private ImageView mForecastImageView;

        public ForecastItemViewHolder(View itemView) {
            super(itemView);
            mForecastTextView = (TextView)itemView.findViewById(R.id.tv_forecast_text);
            mForecastImageView = (ImageView)itemView.findViewById(R.id.weather_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String detailedForecast = mDetailedForecastData.get(getAdapterPosition());
                    mOnForecastItemClickListener.onForecastItemClick(detailedForecast);
                }
            });
        }

        public void bind(String forecast, String icon) {
            mForecastTextView.setText(forecast);
            /**
            try {
                Log.d("Line icon", icon);
                URL url = new URL(icon);
                Bitmap mIcon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                //Bitmap mIcon = BitmapFactory.decodeStream((InputStream)new URL(icon).getContent());
                mForecastImageView.setImageBitmap(mIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }
**/
        }

    }
}