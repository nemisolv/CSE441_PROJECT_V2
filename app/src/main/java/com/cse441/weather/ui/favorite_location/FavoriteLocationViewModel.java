package com.cse441.weather.ui.favorite_location;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cse441.weather.data.model.Location;
import com.cse441.weather.data.model.WeatherForecast;
import com.cse441.weather.data.source.AccuWeatherDataSource;
import com.cse441.weather.data.source.WeatherDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteLocationViewModel extends ViewModel {
    private final MutableLiveData<List<WeatherForecast>> listMutableLiveData = new MutableLiveData<>(new ArrayList<>());
    private final Context context;
    private final WeatherDataSource weatherDataSource;
    private final List<Location> favoriteLocations;

    public FavoriteLocationViewModel(Context context, List<Location> favoriteLocations) {
        this.context = context;
        weatherDataSource = new AccuWeatherDataSource();
        this.favoriteLocations = favoriteLocations;
        loadData();
    }

    public MutableLiveData<List<WeatherForecast>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    private void loadData() {
        List<String> locationKeys = favoriteLocations.stream().map(Location::getKey).collect(Collectors.toList());
        locationKeys.forEach(locationKey -> {
            weatherDataSource.fetchSingleWeather(locationKey, new Callback<WeatherForecast>() {
                @Override
                public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                    if (response.isSuccessful()) {
                        List<WeatherForecast> currentList = listMutableLiveData.getValue();
                        if (currentList != null) {
                            WeatherForecast body =  response.body();
                            assert body != null;
                            body.getDailyForecasts().get(0).setLocation(favoriteLocations.stream().filter(location -> location.getKey().equals(locationKey)).findFirst().orElse(null));
                            currentList.add(body);

                            listMutableLiveData.postValue(currentList);
                        }else {
                            List<WeatherForecast> newList = new ArrayList<>();
                            newList.add(response.body());
                            listMutableLiveData.postValue(newList);
                        }
                    }
                }

                @Override
                public void onFailure(Call<WeatherForecast> call, Throwable t) {
                    // handle the error
                }
            });
        });
    }
}