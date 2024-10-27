package com.cse441.weather.data.model;

import com.google.gson.annotations.SerializedName;

public class PreviewWeather {
    @SerializedName("Key")
    private String key;
    @SerializedName("LocalizedName")
    private String name;
    @SerializedName("Country")
    private Location.Country country;
    @SerializedName("WeatherText")
    private String text;
    @SerializedName("WeatherIcon")
    private int icon;

    @SerializedName("Temperature")
    private TemperaturePreview temperature;
    @SerializedName("Link")
    private String link;
    @SerializedName("EpochTime")
    private String epochTime;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location.Country getCountry() {
        return country;
    }

    public void setCountry(Location.Country country) {
        this.country = country;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public TemperaturePreview getTemperature() {
        return temperature;
    }

    public void setTemperature(TemperaturePreview temperature) {
        this.temperature = temperature;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(String epochTime) {
        this.epochTime = epochTime;
    }

    @Override
    public String toString() {
        return temperature.toString();
    }
}
