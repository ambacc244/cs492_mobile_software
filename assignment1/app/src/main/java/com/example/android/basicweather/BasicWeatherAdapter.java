package com.example.android.basicweather;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class BasicWeatherAdapter extends RecyclerView.Adapter<BasicWeatherAdapter.BasicWeatherViewHolder> {

    private ArrayList<String> mBasicWeatherList;
    private OnBasicWeatherMessageChangeListener mMessageChangeListener;

    public interface OnBasicWeatherMessageChangeListener {
        void onBasicWeatherMessageChanged(int i);
    }

    public BasicWeatherAdapter(OnBasicWeatherMessageChangeListener messageChangeListener){
        mBasicWeatherList = new ArrayList<String>();
        mMessageChangeListener = messageChangeListener;

    }

    public void addBasicWeather(String basicweather){
        mBasicWeatherList.add(basicweather);
    }

    @Override
    public int getItemCount(){
        return mBasicWeatherList.size();
    }

    @NonNull
    @Override
    public BasicWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.basic_weather_item, viewGroup, false);
        return new BasicWeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BasicWeatherViewHolder basicweatherViewHolder, int i){
        String basicweather = mBasicWeatherList.get(i);
        basicweatherViewHolder.bind(basicweather);
    }

    class BasicWeatherViewHolder extends RecyclerView.ViewHolder{
        private TextView mBasicWeatherTV;

        public BasicWeatherViewHolder(final View itemView){
            super(itemView);
            mBasicWeatherTV = itemView.findViewById(R.id.tv_basicweather_text);

            TextView textView = itemView.findViewById(R.id.tv_basicweather_text);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    mMessageChangeListener.onBasicWeatherMessageChanged(index);
                }
            });
        }

        public void bind(String basicweather){
            mBasicWeatherTV.setText(basicweather);
        }
    }
}
