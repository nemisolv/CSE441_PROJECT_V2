package com.cse441.weather.ui.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cse441.weather.data.model.GeoPosition;
import com.cse441.weather.data.model.Location;
import com.cse441.weather.data.model.PreviewWeather;
import com.cse441.weather.data.model.WeatherForecast;
import com.cse441.weather.data.model.hourly.PreviewHourlyForecast;
import com.cse441.weather.data.source.AccuWeatherDataSource;
import com.cse441.weather.data.source.WeatherDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchLocationWeatherViewModel extends ViewModel {
    private final MutableLiveData<List<PreviewWeather>> topCityWeather = new MutableLiveData<>();
    private final MutableLiveData<Location> locationLivedata = new MutableLiveData<>();
    private final MutableLiveData<List<Location> >searchLocations = new MutableLiveData<>();
    private final MutableLiveData<List<Location>> neighbourhoods = new MutableLiveData<>();
    private final WeatherDataSource dataSource;
    @SuppressLint("StaticFieldLeak")
    private final Context context;

    public SearchLocationWeatherViewModel(Context context) {
        this.context = context;
        dataSource = new AccuWeatherDataSource();
        fetchTopCities();
        fetchNeighbourhoods();

    }

    private void fetchNeighbourhoods() {
        GeoPosition coordinates = getLocationFromPreferences();
        if (coordinates != null) {
            dataSource.retrieveLocationByCoordinates(coordinates, new Callback<Location>() {
                @Override
                public void onResponse(Call<Location> call, Response<Location> response) {
                    Location location = response.body();
                    if (location != null) {
                        locationLivedata.setValue(location);
                        dataSource.fetchNeighbourhoods(location.getKey(), new Callback<List<Location>>() {
                            @Override
                            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                                List<Location> locations = response.body();
                                neighbourhoods.setValue(locations);
                            }

                            @Override
                            public void onFailure(Call<List<Location>> call, Throwable t) {
                                neighbourhoods.setValue(null);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<Location> call, Throwable t) {
                    locationLivedata.setValue(null);
                }
            });

        }
    }


    public LiveData<Location> getLocation() {
        return locationLivedata;
    }
    public LiveData<List<Location>> getSearchLocations() {
        return searchLocations;
    }
    public LiveData<List<PreviewWeather>> getTopCityWeather() {
        return topCityWeather;
    }

    public LiveData<List<Location>> getNeighbourhoods() {
        return neighbourhoods;
    }


    public void searchLocation(String query) {
        dataSource.searchLocationByName(query, new Callback<List<Location>>() {
            @Override
            public void onResponse(@NonNull Call<List<Location>> call, @NonNull Response<List<Location>> response) {
                List<Location> locations = response.body();
                Log.d("SearchLocationWeatherViewModel", "onResponse: " + locations.size());
                searchLocations.setValue(locations);
            }

            @Override
            public void onFailure(@NonNull Call<List<Location>> call, @NonNull Throwable t) {
                searchLocations.setValue(null);
            }
        });


    }
    public void fetchTopCities() {
        dataSource.fetchTopWeatherCities(50, new Callback<List<PreviewWeather>>() {
            @Override
            public void onResponse(Call<List<PreviewWeather>> call, Response<List<PreviewWeather>> response) {
                List<PreviewWeather> previewWeathers = response.body();
                topCityWeather.setValue(previewWeathers);
            }

            @Override
            public void onFailure(Call<List<PreviewWeather>> call, Throwable t) {
                topCityWeather.setValue(null);
            }
        });
    }

    private GeoPosition getLocationFromPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("location_prefs", Context.MODE_PRIVATE);
        String latitude = sharedPreferences.getString("latitude", null);
        String longitude = sharedPreferences.getString("longitude", null);

        if (latitude != null && longitude != null) {
            return new GeoPosition(Double.parseDouble(latitude), Double.parseDouble(longitude));
        } else {
            return null;
        }
    }
}