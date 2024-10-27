package com.cse441.weather.ui.favorite_location;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cse441.weather.ui.main.WeatherViewModel;

public class FavoriteLocationViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public FavoriteLocationViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FavoriteLocationViewModel.class)) {
            return (T) new FavoriteLocationViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}