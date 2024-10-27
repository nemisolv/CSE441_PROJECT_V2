package com.cse441.weather.utils;

import com.cse441.weather.R;

public class WeatherUtils {
    public static int mappingWeatherIcon(int iconIndex) {
        switch (iconIndex) {
            case 1:
                return R.drawable.ic_sunny;
            case 2:
                return R.drawable.ic_cloudy;
            case 12:
                return R.drawable.ic_showers;
            case 13:
                return R.drawable.ic_showers;
            case 32:
                    return R.drawable.ic_windy;
            case 22:
                return R.drawable.ic_snowflake;
            default:
                return R.drawable.ic_unknown;
        }

    }
}
