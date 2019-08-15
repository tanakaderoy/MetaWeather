package com.tanaka.metaweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchHistoryActivity extends AppCompatActivity {
    ListView searchHistoryListView;
    WeatherHistoryAdapter adapter;
    ArrayList<WeatherHistory> weatherHistoryArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);

        weatherHistoryArrayList = getIntent().getParcelableArrayListExtra("weatherHistoryArrayList");


        searchHistoryListView = findViewById(R.id.searchHistoryListView);
        adapter = new WeatherHistoryAdapter(this, weatherHistoryArrayList);
        searchHistoryListView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
