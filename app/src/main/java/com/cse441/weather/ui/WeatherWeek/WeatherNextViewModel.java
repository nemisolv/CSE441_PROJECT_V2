package com.cse441.weather.ui.WeatherWeek;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cse441.weather.data.model.WeatherForecast;
import com.cse441.weather.data.model.ingradients.DailyForecast;
import com.cse441.weather.data.source.WeatherDataSource;
import com.cse441.weather.dto.ShortWeatherExchange;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherNextViewModel extends ViewModel {
    private final Context mContext;
    private final WeatherDataSource mDataSource;
    private final ShortWeatherExchange location;


    private MutableLiveData<List<DailyForecast>> nextWeatherForecasts = new MutableLiveData<>();
    public LiveData<List<DailyForecast>> nextWeatherForecastsLiveData = nextWeatherForecasts;


    public WeatherNextViewModel(Context mContext, WeatherDataSource mDataSource, ShortWeatherExchange location) {
        this.mContext = mContext;
        this.mDataSource = mDataSource;
        this.location = location;
        loadData();
    }

    private void loadData() {
        mDataSource.fetch5DaysForecast(location.getLocationKey(), new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                WeatherForecast weatherForecasts = response.body();
                if (weatherForecasts != null) {
                    nextWeatherForecasts.postValue(weatherForecasts.getDailyForecasts());
                }
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {


            }
        });
    }


}
