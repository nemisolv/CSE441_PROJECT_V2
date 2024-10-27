package com.cse441.weather.ui.search;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SearchLocationWeatherViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public SearchLocationWeatherViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchLocationWeatherViewModel.class)) {
            return (T) new SearchLocationWeatherViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}