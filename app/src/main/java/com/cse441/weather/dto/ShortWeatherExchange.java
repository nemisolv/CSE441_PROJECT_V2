package com.cse441.weather.dto;

import java.io.Serializable;

public class ShortWeatherExchange implements Serializable {
    private String locationName;
    private String locationKey;
    private int temperature;
    private int icon;

    public ShortWeatherExchange(String locationName, String locationKey, int temperature, int icon) {
        this.locationName = locationName;
        this.locationKey = locationKey;
        this.temperature = temperature;
        this.icon = icon;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
