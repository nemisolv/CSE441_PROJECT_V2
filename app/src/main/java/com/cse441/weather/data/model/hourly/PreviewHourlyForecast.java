package com.cse441.weather.data.model.hourly;

import com.google.gson.annotations.SerializedName;

public class PreviewHourlyForecast {
    @SerializedName("DateTime")
    private String dateTime;
    @SerializedName("EpochDateTime")
    private long epochTime;

    private String locationKey;

    @SerializedName("WeatherIcon")
    private int icon;
    @SerializedName("IconPhrase")
    private String iconPhrase;

    @SerializedName("IsDaylight")
    private boolean isDaylight;

    @SerializedName("Temperature")
    private BasicTemperature temperature;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public long getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(long epochTime) {
        this.epochTime = epochTime;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        this.iconPhrase = iconPhrase;
    }

    public boolean isDaylight() {
        return isDaylight;
    }

    public void setDaylight(boolean daylight) {
        isDaylight = daylight;
    }

    public BasicTemperature getTemperature() {

        return temperature;
    }

    public void setTemperature(BasicTemperature temperature) {
        this.temperature = temperature;
    }

    public String getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }

    @Override
    public String toString() {
        return "PreviewHourlyForecast{" +
                "dateTime='" + dateTime + '\'' +
                ", epochTime=" + epochTime +
                ", locationKey='" + locationKey + '\'' +
                ", icon=" + icon +
                ", iconPhrase='" + iconPhrase + '\'' +
                ", isDaylight=" + isDaylight +
                ", temperature=" + temperature +
                '}';
    }
}
