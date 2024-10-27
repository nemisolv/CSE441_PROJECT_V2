package com.cse441.weather.ui.DetailedWeather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cse441.weather.data.model.GeoPosition;
import com.cse441.weather.data.model.Location;
import com.cse441.weather.data.model.WeatherForecast;
import com.cse441.weather.data.model.hourly.PreviewHourlyForecast;
import com.cse441.weather.data.source.AccuWeatherDataSource;
import com.cse441.weather.data.source.WeatherDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDetailViewModel extends ViewModel {
    private final MutableLiveData<WeatherForecast> currentWeather = new MutableLiveData<>();
    private final MutableLiveData<Location> locationLivedata = new MutableLiveData<>();
    private final MutableLiveData<List<PreviewHourlyForecast>> hourlyForecast = new MutableLiveData<>();
    private final WeatherDataSource dataSource;
    @SuppressLint("StaticFieldLeak")
    private final Context context;

    public WeatherDetailViewModel(Context context, Location location) {
        this.context = context;
        dataSource = new AccuWeatherDataSource();
        locationLivedata.setValue(location);
    }

    public LiveData<WeatherForecast> getCurrentWeather() {
        return currentWeather;
    }

    public LiveData<Location> getLocation() {
        return locationLivedata;
    }

    public LiveData<List<PreviewHourlyForecast>> getHourlyForecast() {
        return hourlyForecast;
    }

    public void fetchWeatherData() {
        if (locationLivedata.getValue() != null) {
            Location location = locationLivedata.getValue();


            dataSource.fetchSingleWeather(location.getKey(), new Callback<WeatherForecast>() {
                @Override
                public void onResponse(@NonNull Call<WeatherForecast> call, @NonNull Response<WeatherForecast> response) {
                    if (response.isSuccessful()) {
                        currentWeather.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<WeatherForecast> call, @NonNull Throwable t) {
                    currentWeather.setValue(null);

                }
            });
            dataSource.fetchNext12HoursWeather(location.getKey(), new Callback<List<PreviewHourlyForecast>>() {
                @Override
                public void onResponse(Call<List<PreviewHourlyForecast>> call, Response<List<PreviewHourlyForecast>> response) {
                    if (response.isSuccessful()) {
                        hourlyForecast.setValue(response.body());
                    } else {
                        hourlyForecast.setValue(null);
                    }

                }

                @Override
                public void onFailure(Call<List<PreviewHourlyForecast>> call, Throwable t) {
                    hourlyForecast.setValue(null);

                }
            });


        }
    }


}