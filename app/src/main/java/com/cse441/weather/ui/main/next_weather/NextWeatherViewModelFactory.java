package com.cse441.weather.ui.main.next_weather;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cse441.weather.ui.main.WeatherViewModel;

public class NextWeatherViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public NextWeatherViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NextWeatherViewModel.class)) {
            return (T) new NextWeatherViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}