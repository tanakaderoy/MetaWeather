package com.tanaka.metaweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<ConsolidatedWeather> adapter;
    Intent intent;
    ApiInterface apiInterface;
    Retrofit retrofit;
    ArrayList<Location> locationArrayList = new ArrayList<>();
    ArrayList<ConsolidatedWeather> consolidatedWeatherArrayList = new ArrayList<>();
    ListView forecastListView;
    TextView currentCityTextView;
    TextView currentTemperatureTextView;
    LocationManager locationManager;
    LocationListener locationListener;
    android.location.Location currentLocation;
    String latLong = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forecastListView = findViewById(R.id.forecastListView);
        currentCityTextView = findViewById(R.id.currentCityTextView);
        currentTemperatureTextView = findViewById(R.id.currentTemperatureTextView);
        adapter = new WeatherAdapter(this,consolidatedWeatherArrayList);
        forecastListView.setAdapter(adapter);
        setUpRetrofit();


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                Log.i("location", location.toString());
                currentLocation = location;
                fetchDataWithLatLong(currentLocation.getLatitude(),currentLocation.getLongitude());

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
            android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            fetchDataWithLatLong(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        }



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                fetchData();


            default:
                return super.onOptionsItemSelected(item);

        }


    }



    private void fetchDataWithLatLong(Double latitude, Double longitude) {
        consolidatedWeatherArrayList.clear();
        latLong = latitude + "," + longitude;
        apiInterface.getLocationWithLatLong(latLong).enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                List<Location> locations = response.body();

                final Location location = locations.get(0);

                apiInterface.getWeather(location.getWoeid()).enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Gson gson = new Gson();
                        Weather weather = gson.fromJson(response.body(), Weather.class);
                        ConsolidatedWeather todaysWeather = weather.getConsolidatedWeather().get(0);


                        //(1°C × 9/5) + 32 = 33.8°F
                        currentCityTextView.setText(location.getTitle());
                        currentTemperatureTextView.setText(todaysWeather.getTheTemp().intValue() * 9/5 + 32+"°F");
                        for (ConsolidatedWeather consolidatedWeather : weather.getConsolidatedWeather()) {
                            System.out.println(consolidatedWeather.getWeatherStateName());
                            consolidatedWeatherArrayList.add(consolidatedWeather);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });


            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {

            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);

        }
    }


    private void setUpRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.metaweather.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    private void fetchData() {
        consolidatedWeatherArrayList.clear();
        apiInterface.getLocation("Columbus").enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                List<Location> locations = response.body();

                for (Location location : locations) {
                    System.out.println(location.getTitle());

                    locationArrayList.add(location);


                }
                for (final Location location : locationArrayList) {
                    apiInterface.getWeather(location.getWoeid()).enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            Gson gson = new Gson();
                            Weather weather = gson.fromJson(response.body(), Weather.class);
                            ConsolidatedWeather todaysWeather = weather.getConsolidatedWeather().get(0);


                            //(1°C × 9/5) + 32 = 33.8°F
                            currentCityTextView.setText(location.getTitle());
                            currentTemperatureTextView.setText(todaysWeather.getTheTemp().intValue() * 9/5 + 32+"°F");
                            for (ConsolidatedWeather consolidatedWeather : weather.getConsolidatedWeather()) {
                                System.out.println(consolidatedWeather.getWeatherStateName());
                                consolidatedWeatherArrayList.add(consolidatedWeather);
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {

            }
        });
    adapter.notifyDataSetChanged();
    }
}
