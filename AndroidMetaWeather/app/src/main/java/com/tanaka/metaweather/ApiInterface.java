package com.tanaka.metaweather;

import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("location/search/")
    Call<List<Location>> getLocation(@Query("query") String city);

    @GET("location/search/")
    Call<List<Location>> getLocationWithLatLong(@Query("lattlong") String LatLngWithComma);

    @GET("location/{woeid}/")
    Call<JsonElement> getWeather(@Path("woeid") int id);
}