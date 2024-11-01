package com.cse441.weather.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationExchange implements Serializable {

    private String key;

    private String name;


    private String temperature;

    private Country country;

    private String userId;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Constructor
    public LocationExchange() {

    }

    public LocationExchange(String key, String name, Country country) {
        this.key = key;
        this.name = name;
        this.country = country;
    }

    // Getter and setter methods
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "CurrentLocation{" +
                "accuKey='" + key + '\'' +
                ", name='" + name + '\'' +
                ", countryId='" + country.getId() + '\'' +
                '}';
    }

    // Nested class for country data
    public static class Country implements Serializable {

        public Country() {}

        public Country(String id) {
            this.id = id;
        }
        @SerializedName("ID")
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}