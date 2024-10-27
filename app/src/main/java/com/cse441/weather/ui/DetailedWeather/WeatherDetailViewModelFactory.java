package com.cse441.weather.ui.DetailedWeather;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cse441.weather.data.model.Location;

public class WeatherDetailViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    private final Location location;
    public WeatherDetailViewModelFactory(Context context,Location location) {
        this.context = context;
        this.location = location;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherDetailViewModel.class)) {
            return (T) new WeatherDetailViewModel(context,location);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}