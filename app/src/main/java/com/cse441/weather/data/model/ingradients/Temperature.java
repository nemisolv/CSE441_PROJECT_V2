package com.cse441.weather.data.model.ingradients;

import com.google.gson.annotations.SerializedName;

// Class representing Temperature details
public class Temperature {
    @SerializedName("Minimum")
    private ValueType minimum;

    @SerializedName("Maximum")
    private ValueType maximum;

    // Getters
    public ValueType getMinimum() {
        return minimum;
    }

    public ValueType getMaximum() {
        return maximum;
    }

    // Setters
    public void setMinimum(ValueType minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(ValueType maximum) {
        this.maximum = maximum;
    }

    // Nested class for temperature values (Minimum and Maximum)
    public static class ValueType {
        @SerializedName("Value")
        private double value;

        @SerializedName("Unit")
        private String unit;

        @SerializedName("UnitType")
        private int unitType;

        // Getters
        public int getValue() {
            if(unit.equals("F")) {
                return (int) ((value - 32) * 5 / 9);
            }
            return (int) value;
        }

        public String getUnit() {
            return unit;
        }

        public int getUnitType() {
            return unitType;
        }

        // Setters
        public void setValue(double value) {
            this.value = value;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public void setUnitType(int unitType) {
            this.unitType = unitType;
        }

        @Override
        public String toString() {
            return String.format("%.2f %s", value, unit);
        }
    }

    @Override
    public String toString() {
        return String.format("Temperature{minimum=%s, maximum=%s}", minimum, maximum);
    }
}
