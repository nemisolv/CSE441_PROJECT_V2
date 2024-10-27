package com.cse441.weather.ui.favorite_location;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cse441.weather.data.model.WeatherForecast;
import com.cse441.weather.data.source.AccuWeatherDataSource;
import com.cse441.weather.data.source.WeatherDataSource;

public class FavoriteLocationViewModel extends ViewModel {
    private final MutableLiveData<WeatherForecast> currentWeather = new MutableLiveData<>();
    private final Context context;
    private final WeatherDataSource weatherDataSource;
    public FavoriteLocationViewModel(Context context) {
        this.context = context;
        weatherDataSource = new AccuWeatherDataSource();
        loadData();
    }

    private void loadData() {

    }
}