package com.cse441.weather.data.model;

import com.cse441.weather.data.model.ingradients.DailyForecast;
import com.cse441.weather.data.model.ingradients.Headline;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherForecast {

    @SerializedName("Headline")
    private Headline headline;

    @SerializedName("DailyForecasts")
    private List<DailyForecast> dailyForecasts;

    // my custom
    private Location location;

    // Getters and setters
    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public void setDailyForecasts(List<DailyForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }

    public Location getMyLocation() {
        return location;
    }

    public void setMyLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "WeatherForecast{" +
                "headline=" + headline +
                ", dailyForecasts=" + dailyForecasts +
                '}';
    }
}
