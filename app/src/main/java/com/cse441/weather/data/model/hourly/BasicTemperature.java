package com.cse441.weather.data.model.hourly;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class BasicTemperature {
    @SerializedName("Value")
    private int value;
    @SerializedName("Unit")
    private String unit;
    @SerializedName("UnitType")
    private int unitType;

    public int getValue() {
        if(Objects.equals(unit, "F")) return (int) ((value - 32) * 5.0/9.0);
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    @Override
    public String toString() {
        return "BasicTemperature{" +
                "value=" + value +
                ", unit='" + unit + '\'' +
                ", unitType=" + unitType +
                '}';
    }
}
