package com.cse441.weather.ui.favorite_location;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cse441.weather.data.model.Location;
import com.cse441.weather.ui.main.WeatherViewModel;

import java.util.List;

public class FavoriteLocationViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    private final List<Location> favoriteLocations;

    public FavoriteLocationViewModelFactory(Context context, List<Location> favoriteLocations) {
        this.context = context;
        this.favoriteLocations = favoriteLocations;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FavoriteLocationViewModel.class)) {
            return (T) new FavoriteLocationViewModel(context, favoriteLocations);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}