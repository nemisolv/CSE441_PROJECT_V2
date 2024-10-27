package com.cse441.weather.data.model.ingradients;

import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("Speed")
    private Speed speed;

    @SerializedName("Direction")
    private Direction direction;

    // Getters and Setters for each field
    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public static class Speed {
        @SerializedName("Value")
        private double value; // Wind speed value

        @SerializedName("Unit")
        private String unit;  // Unit of measurement (e.g., "km/h" or "mph")

        // Getters and Setters for each field
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

        @Override
        public String toString() {
            return "Speed{" +
                    "value=" + value +
                    ", unit='" + unit + '\'' +
                    '}';
        }
    }

    public static class Direction {
        @SerializedName("Degrees")
        private int degrees; // Wind direction in degrees

        @SerializedName("Localized")
        private String localized; // Localized direction name (e.g., "N", "S")

        @SerializedName("English")
        private String english; // English direction name (e.g., "North", "South")

        // Getters and Setters for each field
        public int getDegrees() {
            return degrees;
        }

        public void setDegrees(int degrees) {
            this.degrees = degrees;
        }

        public String getLocalized() {
            return localized;
        }

        public void setLocalized(String localized) {
            this.localized = localized;
        }

        public String getEnglish() {
            return english;
        }

        public void setEnglish(String english) {
            this.english = english;
        }

        @Override
        public String toString() {
            return "Direction{" +
                    "degrees=" + degrees +
                    ", localized='" + localized + '\'' +
                    ", english='" + english + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Wind{" +
                "speed=" + speed +
                ", direction=" + direction +
                '}';
    }

}
