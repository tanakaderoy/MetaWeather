package com.tanaka.metaweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    ImageView currentWeatherIcon;
    LocationManager locationManager;
    // LocationListener locationListener;
    android.location.Location currentLocation;
    String latLong = "";


    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println(query);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);


            fetchData(query);

            //use the query to search your data somehow
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forecastListView = findViewById(R.id.forecastListView);
        currentCityTextView = findViewById(R.id.currentCityTextView);
        currentTemperatureTextView = findViewById(R.id.currentTemperatureTextView);
        currentWeatherIcon = findViewById(R.id.currentWeatherIconImageView);
        adapter = new WeatherAdapter(this, consolidatedWeatherArrayList);
        forecastListView.setAdapter(adapter);
        setUpRetrofit();


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(android.location.Location location) {
//                Log.i("location 122", location.toString());
//                currentLocation = location;
//                fetchDataWithLatLong(currentLocation.getLatitude(),currentLocation.getLongitude());
//
//            }
//
//            @Override
//            public void onStatusChanged(String s, int i, Bundle bundle) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String s) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String s) {
//
//            }
//        };


        if (!Intent.ACTION_SEARCH.equals(getIntent().getAction())) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {
                android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    fetchDataWithLatLong(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                }

                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new MyLocationListenerGPS(), null);


            }
        }
            // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);


            handleIntent(getIntent());
        findViewById(R.id.constraintLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

    }

    public class MyLocationListenerGPS implements LocationListener {

        @Override
        public void onLocationChanged(android.location.Location location) {
            Log.i("location 122", location.toString());
            currentLocation = location;
            fetchDataWithLatLong(currentLocation.getLatitude(), currentLocation.getLongitude());
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);


        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.mi_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                fetchData("Columbus");


            default:
                return super.onOptionsItemSelected(item);

        }


    }


    private void fetchDataWithLatLong(Double latitude, Double longitude) {
        consolidatedWeatherArrayList.clear();
        adapter.clear();
        currentCityTextView.setText("");
        currentTemperatureTextView.setText("");
        currentWeatherIcon.setVisibility(View.INVISIBLE);
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
                        if (weather != null) {
                            ConsolidatedWeather todaysWeather = weather.getConsolidatedWeather().get(0);


                            //(1°C × 9/5) + 32 = 33.8°F
                            currentCityTextView.setText(location.getTitle());
                            currentTemperatureTextView.setText(todaysWeather.getTheTemp().intValue() * 9 / 5 + 32 + "°F");
                            System.out.println("https://www.metaweather.com/static/img/weather/png/" + todaysWeather.getWeatherStateAbbr() + ".png");
                            Glide.with(getApplicationContext()).load("https://www.metaweather.com/static/img/weather/png/" + todaysWeather.getWeatherStateAbbr() + ".png").into(currentWeatherIcon);
                            currentWeatherIcon.setVisibility(View.VISIBLE);
                            for (ConsolidatedWeather consolidatedWeather : weather.getConsolidatedWeather()) {
                                System.out.println(consolidatedWeather.getWeatherStateName());
                                consolidatedWeatherArrayList.add(consolidatedWeather);
                            }
                            adapter.notifyDataSetChanged();
                        }
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
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            fetchDataWithLatLong(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new MyLocationListenerGPS(), null);

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

    private void fetchData(String city) {
        consolidatedWeatherArrayList.clear();
        adapter.clear();
        currentCityTextView.setText("");
        currentTemperatureTextView.setText("");
        currentWeatherIcon.setVisibility(View.INVISIBLE);
        apiInterface.getLocation(city).enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {

                List<Location> locations = response.body();

                final Location location = locations.get(0);
                locationArrayList.add(location);

                apiInterface.getWeather(location.getWoeid()).enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Gson gson = new Gson();
                        Weather weather = gson.fromJson(response.body(), Weather.class);
                        ConsolidatedWeather todaysWeather = weather.getConsolidatedWeather().get(0);


                        //(1°C × 9/5) + 32 = 33.8°F
                        currentCityTextView.setText(location.getTitle());
                        currentTemperatureTextView.setText(todaysWeather.getTheTemp().intValue() * 9 / 5 + 32 + "°F");
                        Glide.with(getApplicationContext()).load("https://www.metaweather.com/static/img/weather/png/" + todaysWeather.getWeatherStateAbbr() + ".png").into(currentWeatherIcon);
                        currentWeatherIcon.setVisibility(View.VISIBLE);
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
        adapter.notifyDataSetChanged();
    }
}
