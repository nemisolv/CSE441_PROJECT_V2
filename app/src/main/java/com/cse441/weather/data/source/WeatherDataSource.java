package com.cse441.weather.data.source;

import com.cse441.weather.data.model.GeoPosition;
import com.cse441.weather.data.model.Location;
import com.cse441.weather.data.model.PreviewWeather;
import com.cse441.weather.data.model.WeatherForecast;
import com.cse441.weather.data.model.hourly.PreviewHourlyForecast;

import java.util.List;

import retrofit2.Callback;

public interface WeatherDataSource {
    void fetchSingleWeather(String locationKey, Callback<WeatherForecast> callback);
    void retrieveLocationByCoordinates(GeoPosition geoPosition, Callback<Location> callback);
    void fetchNeighbourhoods(String locationKey, Callback<List<Location>> callback);

    void fetchTopCitiesWorldwide(int limit, Callback<List<Location>> callback);

    void fetchTopWeatherCities(int limit, Callback<List<PreviewWeather>> callback);

    void searchLocationByName(String query, Callback<List<Location>> callback);

    void fetchNext12HoursWeather(String locationKey, Callback<List<PreviewHourlyForecast>> callback);

    void fetch5DaysForecast(String locationKey, Callback<WeatherForecast> callback);
}
