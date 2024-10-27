package com.cse441.weather.data.model.ingradients;

import com.google.gson.annotations.SerializedName;

// Class representing Air and Pollen data
public class AirAndPollen {
    @SerializedName("Name")
    private String name;

    @SerializedName("Value")
    private int value;

    @SerializedName("Category")
    private String category;

    @SerializedName("CategoryValue")
    private int categoryValue;

    @SerializedName("Type")
    private String type;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(int categoryValue) {
        this.categoryValue = categoryValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AirAndPollen{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", category='" + category + '\'' +
                ", categoryValue=" + categoryValue +
                ", type='" + type + '\'' +
                '}';
    }
}
