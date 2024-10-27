package com.cse441.weather.data.model;

import com.cse441.weather.data.model.ingradients.Temperature;
import com.google.gson.annotations.SerializedName;

public class TemperaturePreview {

    @SerializedName("Metric")
    private Temperature.ValueType metric;

    @SerializedName("Imperial")
    private Temperature.ValueType imperial;

    // Getters and setters
    public Temperature.ValueType getMetric() {
        return metric;
    }

    public void setMetric(Temperature.ValueType metric) {
        this.metric = metric;
    }

    public Temperature.ValueType getImperial() {
        return imperial;
    }

    public void setImperial(Temperature.ValueType imperial) {
        this.imperial = imperial;
    }

    // Metric class to map the 'Metric' part of the JSON
//    public static class Metric {
//        @SerializedName("Value")
//        private double value;
//
//        @SerializedName("Unit")
//        private String unit;
//
//        @SerializedName("UnitType")
//        private int unitType;
//
//        // Getters and setters
//        public double getValue() {
//            return value;
//        }
//
//        public void setValue(double value) {
//            this.value = value;
//        }
//
//        public String getUnit() {
//            return unit;
//        }
//
//        public void setUnit(String unit) {
//            this.unit = unit;
//        }
//
//        public int getUnitType() {
//            return unitType;
//        }
//
//        public void setUnitType(int unitType) {
//            this.unitType = unitType;
//        }
//    }
//
//    // Imperial class to map the 'Imperial' part of the JSON
//    public static class Imperial {
//        @SerializedName("Value")
//        private double value;
//
//        @SerializedName("Unit")
//        private String unit;
//
//        @SerializedName("UnitType")
//        private int unitType;
//
//        // Getters and setters
//        public double getValue() {
//            return value;
//        }
//
//        public void setValue(double value) {
//            this.value = value;
//        }
//
//        public String getUnit() {
//            return unit;
//        }
//
//        public void setUnit(String unit) {
//            this.unit = unit;
//        }
//
//        public int getUnitType() {
//            return unitType;
//        }
//
//        public void setUnitType(int unitType) {
//            this.unitType = unitType;
//        }
//    }
}

