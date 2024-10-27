package com.cse441.weather.data.source;

import android.util.Log;

import com.cse441.weather.data.model.GeoPosition;
import com.cse441.weather.data.model.Location;
import com.cse441.weather.data.model.PreviewWeather;
import com.cse441.weather.data.model.WeatherForecast;
import com.cse441.weather.data.model.hourly.PreviewHourlyForecast;
import com.cse441.weather.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccuWeatherDataSource implements WeatherDataSource {

    private static final String TAG = "AccuWeatherDataSource";
    private final ApiService apiService;

    public AccuWeatherDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }


    @Override
    public void fetchSingleWeather(String locationKey, Callback<WeatherForecast> callback) {
        Call<WeatherForecast> call = apiService.findDetailedSingleWeather(locationKey, Constants.API_KEY);
        call.enqueue(new WeatherCallback<>(callback, "Error fetching detailed single weather data"));
    }


    @Override
    public void retrieveLocationByCoordinates(GeoPosition geoPosition, Callback<Location> callback) {
        double longitude = geoPosition.getLongitude();
        double latitude = geoPosition.getLatitude();
        String longLat = latitude + "," + longitude;
        Log.d(TAG, "findLocationByLongLat: " + longLat);
        Call<Location> call = apiService.findLocationByLongLat(longLat, Constants.API_KEY);
        call.enqueue(new WeatherCallback<>(callback, "Error fetching location by long-lat"));
    }

    @Override
    public void fetchNeighbourhoods(String locationKey, Callback<List<Location>> callback) {
        if (locationKey == null) {
            throw new IllegalArgumentException("Location key cannot be null");
        }

        Call<List<Location>> call = apiService.findNeighbourhoodWeather(locationKey, Constants.API_KEY);
        call.enqueue(new WeatherCallback<>(callback, "Error fetching neighbourhood weather"));
    }

    @Override
    public void fetchTopCitiesWorldwide(int limit, Callback<List<Location>> callback) {
        Call<List<Location>> call = apiService.findTopCitiesWorldwide(limit, Constants.API_KEY);
        call.enqueue(new WeatherCallback<>(callback, "Error fetching top cities worldwide"));
    }

    @Override
    public void fetchTopWeatherCities(int limit, Callback<List<PreviewWeather>> callback) {
        Call<List<PreviewWeather>> call = apiService.findWeatherTopCities(limit, Constants.API_KEY);
        call.enqueue(new WeatherCallback<>(callback, "Error fetching top cities weather"));
    }

    @Override
    public void searchLocationByName(String query, Callback<List<Location>> callback) {
        // Ensure the query is not null or empty
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Search query cannot be null or empty");
        }

        // Call the ApiService method to search locations by name
        Call<List<Location>> call = apiService.searchLocationByName(query, Constants.API_KEY);

        // Enqueue the call with a callback to handle the response
        call.enqueue(new WeatherCallback<>(callback, "Error fetching location suggestions"));
    }

    @Override
    public void fetchNext12HoursWeather(String locationKey, Callback<List<PreviewHourlyForecast>> callback) {
        Call<List<PreviewHourlyForecast>> call = apiService.findNext12HoursWeather(locationKey, Constants.API_KEY);
        call.enqueue(new WeatherCallback<>(callback, "Error fetching next 12 hours weather"));
    }

    @Override
    public void fetch5DaysForecast(String locationKey, Callback<WeatherForecast> callback) {
        Call<WeatherForecast> call = apiService.find5DaysForecast(locationKey, Constants.API_KEY);
        call.enqueue(new WeatherCallback<>(callback, "Error fetching 5 days forecast"));
    }


    // Helper class to handle response
    private static class WeatherCallback<T> implements Callback<T> {

        private final Callback<T> callback;
        private final String errorMessage;

        public WeatherCallback(Callback<T> callback, String errorMessage) {
            this.callback = callback;
            this.errorMessage = errorMessage;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (response.isSuccessful() && response.body() != null) {
                callback.onResponse(call, response);
            } else {
                Log.e(TAG, errorMessage + ": " + response.message() + " (Code: " + response.code() + ")");
                callback.onFailure(call, new Exception(errorMessage + ": " + response.message()));
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            Log.e(TAG, errorMessage, t);
            callback.onFailure(call, t);
        }
    }
}
