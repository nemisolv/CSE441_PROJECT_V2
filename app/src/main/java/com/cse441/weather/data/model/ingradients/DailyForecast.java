package com.cse441.weather.data.model.ingradients;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyForecast {

    @SerializedName("Date")
    private String date;

    @SerializedName("EpochDate")
    private long epochDate;

    @SerializedName("Sun")
        private Sun sun;

    @SerializedName("Moon")
    private Moon moon;

    @SerializedName("Temperature")
    private Temperature temperature;

    @SerializedName("RealFeelTemperature")
    private RealFeelTemperature realFeelTemperature;


    @SerializedName("HoursOfSun")
    private double hoursOfSun;


    @SerializedName("AirAndPollen")
    private List<AirAndPollen> airAndPollen;

    @SerializedName("Day")
    private DayNight day;

    @SerializedName("Night")
    private DayNight night;

    @SerializedName("Link")
    private String link;

    // Getters and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getEpochDate() {
        return epochDate;
    }

    public void setEpochDate(long epochDate) {
        this.epochDate = epochDate;
    }

    public Sun getSun() {
        return sun;
    }

    public void setSun(Sun sun) {
        this.sun = sun;
    }

    public Moon getMoon() {
        return moon;
    }

    public void setMoon(Moon moon) {
        this.moon = moon;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public RealFeelTemperature getRealFeelTemperature() {
        return realFeelTemperature;
    }

    public void setRealFeelTemperature(RealFeelTemperature realFeelTemperature) {
        this.realFeelTemperature = realFeelTemperature;
    }

    public double getHoursOfSun() {
        return hoursOfSun;
    }

    public void setHoursOfSun(double hoursOfSun) {
        this.hoursOfSun = hoursOfSun;
    }

    public List<AirAndPollen> getAirAndPollen() {
        return airAndPollen;
    }

    public void setAirAndPollen(List<AirAndPollen> airAndPollen) {
        this.airAndPollen = airAndPollen;
    }

    public DayNight getDay() {
        return day;
    }

    public void setDay(DayNight day) {
        this.day = day;
    }

    public DayNight getNight() {
        return night;
    }

    public void setNight(DayNight night) {
        this.night = night;
    }



    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "DailyForecast{" +
                "date=" + date +
                ", epochDate=" + epochDate +
                ", sun=" + sun +
                ", moon=" + moon +
                ", temperature=" + temperature +
                ", realFeelTemperature=" + realFeelTemperature +
                ", hoursOfSun=" + hoursOfSun +
                ", airAndPollen=" + airAndPollen +
                ", day=" + day +
                ", night=" + night +
                ", link='" + link + '\'' +
                '}';
    }
}
