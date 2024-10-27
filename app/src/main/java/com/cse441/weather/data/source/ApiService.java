package com.cse441.weather.data.source;

import com.cse441.weather.data.model.Location;
import com.cse441.weather.data.model.PreviewWeather;
import com.cse441.weather.data.model.WeatherForecast;
import com.cse441.weather.data.model.hourly.PreviewHourlyForecast;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/forecasts/v1/daily/{period}/{locationKey}?language=en")
    Call<List<WeatherForecast>> findDetailedWeather(
            @Path("period") String period,  // Can be "5day", "10day", "1day", etc.
            @Path("locationKey") String locationKey,
            @Query("details") boolean details,  // This should be a query parameter
            @Query("apikey") String apiKey
    );


    @GET("/forecasts/v1/daily/1day/{locationKey}?language=en&details=true")
    Call<WeatherForecast> findDetailedSingleWeather(
            @Path("locationKey") String locationKey,
            @Query("apikey") String apiKey
    );


    @GET("/locations/v1/cities/neighbors/{locationKey}?language=en")
    Call<List<Location>> findNeighbourhoodWeather(@Path("locationKey") String locationKey, @Query("apikey") String apiKey);

    @GET("/locations/v1/cities/geoposition/search")
    Call<Location> findLocationByLongLat(@Query("q") String longLat, @Query("apikey") String apiKey);

    @GET("/locations/v1/topcities/{limit}")
    Call<List<Location>> findTopCitiesWorldwide(
            @Path("limit") int limit,
            @Query("apikey") String apiKey);


    @GET("/currentconditions/v1/topcities/{limit}?language=en")
    Call<List<PreviewWeather>> findWeatherTopCities(
            @Path("limit") int limit,
            @Query("apikey") String apiKey
    );

    @GET("/locations/v1/cities/autocomplete?language=vi")
    Call<List<Location>> searchLocationByName(@Query("q") String keySearch, @Query("apikey") String apiKey);

    // get next 12 hours weather
    @GET("/forecasts/v1/hourly/12hour/{locationKey}?language=en&details=true")
    Call<List<PreviewHourlyForecast>> findNext12HoursWeather(
            @Path("locationKey") String locationKey,
            @Query("apikey") String apiKey
    );

    // get 5 days forecast
    @GET("/forecasts/v1/daily/5day/{locationKey}?language=en&details=true")
    Call<WeatherForecast> find5DaysForecast(
            @Path("locationKey") String locationKey,
            @Query("apikey") String apiKey
    );
}
