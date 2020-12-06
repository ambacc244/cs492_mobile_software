package com.example.android.sqliteweather;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;

import com.example.android.sqliteweather.data.Location;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationItemViewHolder> {

    private List<Location> mLocationItems;
    //private OnLocationItemClickListener mLocationItemClickListener;
/**
    public interface OnLocationItemClickListener {
        void onLocationItemClick(Location location);
    }

    public LocationAdapter(OnLocationItemClickListener clickListener) {
        mLocationItemClickListener = clickListener;
    }
**/

    public LocationAdapter() {

    }

    public void updateLocationItems(List<Location> locationItems) {
        mLocationItems = locationItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mLocationItems != null) {
            return mLocationItems.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public LocationItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.location_list_item, parent, false);
        return new LocationItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LocationItemViewHolder holder, int position) {
        holder.bind(mLocationItems.get(position));
    }

    class LocationItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mLocationDateTV;

        public LocationItemViewHolder(View itemView) {
            super(itemView);
            mLocationDateTV = itemView.findViewById(R.id.tv_location_data);
/**
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Location locationItem = mLocationItems.get(getAdapterPosition());
              //      mLocationItemClickListener.onLocationItemClick(locationItem);
                }
            });**/
        }

        public void bind(Location locationItem) {
            Log.d("TAGTAG", locationItem.location);
            mLocationDateTV.setText(locationItem.location);
        }
/**
        @Override
        public void onClick(View v) {
            Location locationItem = mLocationItems.get(getAdapterPosition());
            mLocationItemClickListener.onLocationItemClick(locationItem);
        }**/
    }
}
