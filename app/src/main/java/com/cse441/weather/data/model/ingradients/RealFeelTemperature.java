package com.cse441.weather.data.model.ingradients;

import com.google.gson.annotations.SerializedName;

// Class representing RealFeelTemperature details
public class RealFeelTemperature {
    @SerializedName("Minimum")
    private RealFeelValue minimum;

    @SerializedName("Maximum")
    private RealFeelValue maximum;

    // Getters and Setters
    public RealFeelValue getMinimum() {
        return minimum;
    }

    public void setMinimum(RealFeelValue minimum) {
        this.minimum = minimum;
    }

    public RealFeelValue getMaximum() {
        return maximum;
    }

    public void setMaximum(RealFeelValue maximum) {
        this.maximum = maximum;
    }

    // Nested class for RealFeel temperature values (Minimum and Maximum) with phrase
    public static class RealFeelValue {
        @SerializedName("Value")
        private double value;

        @SerializedName("Unit")
        private String unit;

        @SerializedName("UnitType")
        private int unitType;

        @SerializedName("Phrase")
        private String phrase;

        // Getters and Setters
        public double getValue() {
            return value;
        }

        public void setValue(double value) {
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

        public String getPhrase() {
            return phrase;
        }

        public void setPhrase(String phrase) {
            this.phrase = phrase;
        }

        @Override
        public String toString() {
            return "RealFeelValue{" +
                    "value=" + value +
                    ", unit='" + unit + '\'' +
                    ", unitType=" + unitType +
                    ", phrase='" + phrase + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RealFeelTemperature{" +
                "minimum=" + minimum +
                ", maximum=" + maximum +
                '}';
    }
}
