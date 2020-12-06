package com.example.yeongaelee.final_project;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yeongaelee.final_project.data.DietItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    public static int [] editModelArrayList;
    private ArrayList<DietItem> mDietItems;
    private ArrayList<DietItem> mSearchDietItems;
    private final int SIZE = 10;

    public CustomAdapter(int[] editModelArrayList){
        mDietItems = new ArrayList<DietItem>();
        mSearchDietItems = new ArrayList<DietItem>();

        this.editModelArrayList = editModelArrayList;

        for(int i = 0; i<SIZE; i++) {
            Log.d("TAG", "should be 0: " + i + " = " + editModelArrayList[i]);
        }
    }

    public void addItem(DietItem item){
        mDietItems.add(item);
        mSearchDietItems.add(item);
        notifyDataSetChanged();
    }

    public int getDietItemCalorie(int i){
        return mDietItems.get(i).calorie;
    }

    public int [] getValue() {
        return editModelArrayList;
    }

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
        mDietItems.clear();
        if (charText.length() == 0) {
            mDietItems.addAll(mSearchDietItems);
        } else {
            for (DietItem dietItem : mSearchDietItems) {
                String name = dietItem.name;

                if (name.toLowerCase().contains(charText)) {
                    mDietItems.add(dietItem);
                }
            }
        }
        notifyDataSetChanged();
    }




    @Override
    public int getItemCount() {
        return mDietItems.size();
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diet_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.MyViewHolder holder, final int position) {
        holder.bind(mDietItems.get(position));
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        protected EditText editText;
        private TextView mDietDataTV;

        public MyViewHolder(View itemView) {
            super(itemView);

            editText = (EditText) itemView.findViewById(R.id.ed_diet_calorie);
            mDietDataTV = itemView.findViewById(R.id.tv_diet_text);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if(editText.getText().toString().equals(""))
                        editModelArrayList[getAdapterPosition()] = 0;
                    else
                        editModelArrayList[getAdapterPosition()] = Integer.parseInt(editText.getText().toString());

                    Log.d("TAG", "Save new: " + editModelArrayList[getAdapterPosition()]);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        }

        public void bind(DietItem dietItem) {
            //Log.d("TAG", dietItem.name);
            mDietDataTV.setText(dietItem.name);
        }
    }
}
