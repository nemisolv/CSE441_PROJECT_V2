package com.cse441.weather.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {

    @SerializedName("Key")
    private String key;

    @SerializedName("LocalizedName")
    private String name;


    // optional (no mapping from JSON)
    private String temperature;

    @SerializedName("Country")
    private Country country;

    // Constructor
    public Location() {

    }

    public Location(String key, String name, Country country) {
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