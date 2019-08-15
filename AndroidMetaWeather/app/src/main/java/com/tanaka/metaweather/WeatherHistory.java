package com.tanaka.metaweather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeatherHistory implements Parcelable {
    private String title;

    private Integer woeid;
    private String locationType;

    public WeatherHistory(String title, Integer woeid, String locationType) {
        this.title = title;

        this.woeid = woeid;
        this.locationType = locationType;
    }

    public String getTitle() {
        return title;
    }


    public String getStringTimeStamp() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("E, MMM dd h:mm a");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public Integer getWoeid() {
        return woeid;
    }

    public String getLocationType() {
        return locationType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeValue(this.woeid);
        dest.writeString(this.locationType);
    }

    protected WeatherHistory(Parcel in) {
        this.title = in.readString();
        this.woeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.locationType = in.readString();
    }

    public static final Parcelable.Creator<WeatherHistory> CREATOR = new Parcelable.Creator<WeatherHistory>() {
        @Override
        public WeatherHistory createFromParcel(Parcel source) {
            return new WeatherHistory(source);
        }

        @Override
        public WeatherHistory[] newArray(int size) {
            return new WeatherHistory[size];
        }
    };
}
