package com.cse441.weather.ui.main.next_weather;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cse441.weather.data.model.GeoPosition;
import com.cse441.weather.data.model.Location;
import com.cse441.weather.data.model.PreviewWeather;
import com.cse441.weather.data.model.WeatherForecast;
import com.cse441.weather.data.model.hourly.PreviewHourlyForecast;
import com.cse441.weather.data.source.AccuWeatherDataSource;
import com.cse441.weather.data.source.WeatherDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NextWeatherViewModel extends ViewModel {

    private final MutableLiveData<WeatherForecast> nextWeather = new MutableLiveData<>();
    private final MutableLiveData<Location> locationLivedata = new MutableLiveData<>();
    private final Context context;

    private final WeatherDataSource weatherDataSource;

    public NextWeatherViewModel(Context context) {
        this.context = context;
        weatherDataSource = new AccuWeatherDataSource();
        fetchNextWeather();
    }

    public LiveData<WeatherForecast> getNextWeather() {
        return nextWeather;
    }

    public LiveData<Location> getLocation() {
        return locationLivedata;
    }

    public void fetchNextWeather() {
        GeoPosition coordinates = getLocationFromPreferences();
        if (coordinates != null) {
            weatherDataSource.retrieveLocationByCoordinates(coordinates, new Callback<Location>() {
                @Override
                public void onResponse(@NonNull Call<Location> call, @NonNull Response<Location> response) {
                    if (response.isSuccessful()) {
                        Location location = response.body();

                        assert location != null;
                        locationLivedata.setValue(location);

                        weatherDataSource.fetch5DaysForecast(location.getKey(), new Callback<WeatherForecast>() {
                            @Override
                            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                                if (response.isSuccessful()) {
                                    WeatherForecast weatherForecast = response.body();
                                    assert weatherForecast != null;
                                    nextWeather.setValue(weatherForecast);
                                }
                            }

                            @Override
                            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                                nextWeather.setValue(null);

                            }
                        });

                    }
                }

                @Override
                public void onFailure(@NonNull Call<Location> call, @NonNull Throwable t) {
                    locationLivedata.setValue(null);
                }
            });
        }
    }

    private GeoPosition getLocationFromPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("location_prefs", Context.MODE_PRIVATE);
        String latitude = sharedPreferences.getString("latitude", null);
        String longitude = sharedPreferences.getString("longitude", null);

        if (latitude != null && longitude != null) {
            return new GeoPosition(Double.parseDouble(latitude), Double.parseDouble(longitude));
        } else {
            return null;
        }
    }



}