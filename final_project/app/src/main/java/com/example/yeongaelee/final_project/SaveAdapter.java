package com.example.yeongaelee.final_project;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yeongaelee.final_project.data.SaveItem;

import java.util.List;

public class SaveAdapter extends RecyclerView.Adapter<SaveAdapter.SaveItemViewHolder> {

    private List<SaveItem> mSaveItems;

    public SaveAdapter() {
    }

    public void updateSaveItems(List<SaveItem> saveItems) {
        mSaveItems = saveItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mSaveItems != null) {
            return mSaveItems.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public SaveItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.save_list_item, parent, false);
        return new SaveItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SaveItemViewHolder holder, int position) {
        holder.bind(mSaveItems.get(position));
    }

    class SaveItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mSaveDataTV;

        public SaveItemViewHolder(View itemView) {
            super(itemView);
            mSaveDataTV = itemView.findViewById(R.id.tv_save_text);
        }

        public void bind(SaveItem saveItem) {
            mSaveDataTV.setText(saveItem.date + ": " + saveItem.calorie + " cal");
        }
    }
}