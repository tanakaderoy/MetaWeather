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
 * Created by Tanaka Mazi on 2019-08-12.
 * Copyright (c) 2019 All rights reserved.
 */
public class WeatherAdapter extends ArrayAdapter<ConsolidatedWeather> {

    private Context context;
    private List<ConsolidatedWeather> weatherList = new ArrayList<>();

    public WeatherAdapter(@NonNull Context context, @LayoutRes ArrayList<ConsolidatedWeather> list) {
        super(context, 0, list);
        this.context = context;
        weatherList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.forecast_list, parent, false);


        ConsolidatedWeather weather = weatherList.get(position);

        ImageView image = listItem.findViewById(R.id.weatherIconImageView);
        Glide.with(listItem).load("https://www.metaweather.com/static/img/weather/png/" + weather.getWeatherStateAbbr() + ".png").into(image);
        System.out.println("https://www.metaweather.com/static/img/weather/png/" + weather.getWeatherStateAbbr() + ".png");
        image.setMaxWidth(100);
        image.setMaxHeight(100);

        TextView iconDescription = listItem.findViewById(R.id.iconDescriptionTextView);
        TextView dateTextView = listItem.findViewById(R.id.dateTextView);
        //TextView humidityTextView = listItem.findViewById(R.id.humidityTextView);
        TextView humidityTextView = listItem.findViewById(R.id.humidityTextView);
        TextView maxTemp = listItem.findViewById(R.id.maxTempTextVIew);
        TextView minTemp = listItem.findViewById(R.id.minTempTextView);
        TextView percentChance = listItem.findViewById(R.id.percentChanceTextView);

        iconDescription.setText(weather.getWeatherStateName());
        String pattern = "E, MMM dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");


        try {
            Date unFormatteddate = format2.parse(weather.getApplicableDate());

            dateTextView.setText(simpleDateFormat.format(unFormatteddate));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        humidityTextView.setText(weather.getHumidity().toString());
        String maxTempInF = Integer.toString(weather.getMaxTemp().intValue() * 9 / 5 + 32);
        String minTempInF = Integer.toString(weather.getMinTemp().intValue() * 9 / 5 + 32);

        maxTemp.setText("Max: " + maxTempInF + "°F");
        minTemp.setText("Min: " + minTempInF + "°F");
        percentChance.setText(weather.getPredictability() + "%");
        return listItem;
    }


}
