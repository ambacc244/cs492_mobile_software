package com.example.yeongaelee.final_project;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.yeongaelee.final_project.data.SaveItem;

import java.util.Collections;
import java.util.List;

public class SaveActivity extends AppCompatActivity {

    private RecyclerView mSaveRV;
    private SaveAdapter mSaveAdapter;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_activity);

        mSaveRV = findViewById(R.id.rv_save_list);

        mSaveAdapter = new SaveAdapter();

        mSaveRV.setLayoutManager(new LinearLayoutManager(this));
        mSaveRV.setHasFixedSize(true);

        mSaveRV.setAdapter(mSaveAdapter);

        SaveViewModel viewModel = ViewModelProviders.of(this).get(SaveViewModel.class);

        viewModel.getAllSaveItems().observe(this, new Observer<List<SaveItem>>() {
            @Override
            public void onChanged(@Nullable List<SaveItem> saveItems) {
                Collections.reverse(saveItems);
                mSaveAdapter.updateSaveItems(saveItems);
            }
        });
    }
}
