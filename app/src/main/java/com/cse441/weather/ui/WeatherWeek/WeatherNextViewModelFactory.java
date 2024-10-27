package com.cse441.weather.ui.WeatherWeek;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cse441.weather.data.source.WeatherDataSource;
import com.cse441.weather.dto.ShortWeatherExchange;

public class WeatherNextViewModelFactory  implements
        ViewModelProvider.Factory {
    private final WeatherDataSource mDataSource;
    private final Context mContext;
    private final ShortWeatherExchange location;

    public WeatherNextViewModelFactory(Context context, WeatherDataSource dataSource, ShortWeatherExchange location) {
        mDataSource = dataSource;
        mContext = context;
        this.location = location;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherNextViewModel.class)) {
            return (T) new WeatherNextViewModel(mContext,mDataSource, location);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}