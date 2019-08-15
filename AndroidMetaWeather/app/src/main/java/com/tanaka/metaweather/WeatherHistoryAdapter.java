package com.tanaka.metaweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tanaka Mazi on 2019-08-15.
 * Copyright (c) 2019 All rights reserved.
 */
public class WeatherHistoryAdapter extends ArrayAdapter<WeatherHistory> {
    private Context context;
    private List<WeatherHistory> weatherHistoryList;

    public WeatherHistoryAdapter(@NonNull Context context, @LayoutRes ArrayList<WeatherHistory> list) {
        super(context, 0, list);
        this.context = context;
        weatherHistoryList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.weather_history_list, parent, false);


        WeatherHistory weatherHistory = weatherHistoryList.get(position);
        TextView titleTextView = listItem.findViewById(R.id.titleTextView);
        TextView locationTypeTextView = listItem.findViewById(R.id.locationTypeTextView);
        TextView woeidTextView = listItem.findViewById(R.id.woeidTextView);
        TextView timeStampTextView = listItem.findViewById(R.id.timeStampTextView);
        titleTextView.setText(weatherHistory.getTitle());
        timeStampTextView.setText(weatherHistory.getStringTimeStamp());
        locationTypeTextView.setText(weatherHistory.getLocationType());
        woeidTextView.setText(String.valueOf(weatherHistory.getWoeid()));

        return listItem;
    }
}
