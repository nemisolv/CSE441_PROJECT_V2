package com.cse441.weather.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.cse441.weather.R;
import com.cse441.weather.data.model.Location;
import com.cse441.weather.data.model.WeatherForecast;
import com.cse441.weather.data.model.ingradients.AirAndPollen;
import com.cse441.weather.data.model.ingradients.DayNight;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utility {
    public static boolean validateLocation(Location location) {
        String locationKey = location.getKey();
        String locationName = location.getName();
        String countryId = location.getCountry().getId();
        return locationKey != null && !locationKey.isEmpty() &&
                locationName != null && !locationName.isEmpty() &&
                countryId != null && !countryId.isEmpty();
    }

    public static String formatDegree(double degree) {
        return String.format("%.1f", degree);
    }


public static String formatTime(LocalDateTime time) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm a"));
    }
    return "N/A";
}

    @SuppressLint("SetTextI18n")
//    public static void setBasicValueUIDetail(WeatherForecast weatherForecast, View view) {
//
//        // Fetching UI elements from the provided view
//        ImageView iconWeather = view.findViewById(R.id.img_weather_icon_detail);
//        TextView txtLocation = view.findViewById(R.id.txt_location_current);
//        TextView txtDegree = view.findViewById(R.id.txt_degrees);
//        TextView txtTime = view.findViewById(R.id.txt_time_current);
//        TextView txtUV = view.findViewById(R.id.txt_uv_current);
//        TextView txtPrecipitationRain = view.findViewById(R.id.txt_precipitation_rain);
//        TextView txtShortAQ = view.findViewById(R.id.txt_aq_current);
//        TextView txtCalendar = view.findViewById(R.id.txt_calendar);
//        TextView txtDateFormat = view.findViewById(R.id.txt_date_format);
//        TextView txtSunrise = view.findViewById(R.id.txt_sunrise);
//        TextView txtSunset = view.findViewById(R.id.txt_sunset);
//        TextView txtAirQuality = view.findViewById(R.id.txt_air_quality);
//        TextView txtWindSpeed = view.findViewById(R.id.txt_wind_speed_day);
//        // Extracting weather data
//        String locationName = weatherForecast.getMyLocation().getName();
//        double temperature = weatherForecast.getDailyForecasts().get(0).getTemperature().getMaximum().getValue();
//        String time = weatherForecast.getDailyForecasts().get(0).getDate();
//        long sunrise = weatherForecast.getDailyForecasts().get(0).getSun().getEpochRise();
//        long sunset = weatherForecast.getDailyForecasts().get(0).getSun().getEpochSet();
//        List<AirAndPollen> airAndPollen = weatherForecast.getDailyForecasts().get(0).getAirAndPollen();
//
//        // Finding UV and Air Quality values
//        AirAndPollen uv = airAndPollen.stream().filter(a -> a.getName().equals("UVIndex")).findFirst().orElse(null);
//        AirAndPollen aq = airAndPollen.stream().filter(a -> a.getName().equals("AirQuality")).findFirst().orElse(null);
//
//        // Setting UV index
//        if (uv != null) {
//            txtUV.setText(uv.getValue() + "");
//        } else {
//            txtUV.setText("N/A");
//        }
//
//        // Setting Air Quality index
//        if (aq != null) {
//            Log.d("TAG", "setValueUI: " + aq);
//            txtShortAQ.setText(aq.getValue() + "");
//            txtAirQuality.setText(aq.getValue() + " - " +  aq.getCategory());
//        } else {
//            txtShortAQ.setText("N/A");
//            txtAirQuality.setText("N/A");
//        }
//
//        // Updating other UI elements
////        txtWindSpeed.setText( weatherForecast.getDailyForecasts().get(0).getDay().getWind().getSpeed().getValue() + " mi/h");
////            txtSunrise.setText("Sunrise: " + LocalDateTime.ofEpochSecond(sunrise, 0, null));
////        txtSunset.setText("Sunset: " + Utility.formatTime(LocalDateTime.ofEpochSecond(sunset, 0, null)));
//        txtLocation.setText(locationName);
//        txtDegree.setText((int) temperature + "°C");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            txtTime.setText(Utility.formatTime(LocalDateTime.now()));
//            txtTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm a")));
//        }
//        txtCalendar.setText(Utility.formatDate(time));
//
//        // Determine if it's night or day
//        LocalDateTime now = null;
//        boolean isNight = false;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            now = LocalDateTime.now();
//            isNight = now.getHour() >= 18 || now.getHour() <= 6;
//        }
//
//        // Set weather data based on day or night
//        if (!isNight) {
//            DayNight day = weatherForecast.getDailyForecasts().get(0).getDay();
//            txtPrecipitationRain.setText(day.getRainProbability() + "");
//            iconWeather.setImageResource(WeatherUtils.mappingWeatherIcon(day.getIcon()));
//        } else {
//            DayNight night = weatherForecast.getDailyForecasts().get(0).getNight();
//            txtPrecipitationRain.setText(night.getRainProbability() + "");
//            iconWeather.setImageResource(WeatherUtils.mappingWeatherIcon(night.getIcon()));
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static String getTimeHourly(String dateTime) {
//        // format like: 15:00
//        try {
//            ZonedDateTime zdt = ZonedDateTime.parse(dateTime);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:00", Locale.getDefault());
//            return zdt.format(formatter);
//        } catch (DateTimeParseException e) {
//            e.printStackTrace();
//            return "Invalid date-time format";
//        }
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String mapTimeToDayOfWeek(String time) {
        // time -> monday, tuesday...
        try {
            ZonedDateTime zdt = ZonedDateTime.parse(time);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", Locale.getDefault());
            return zdt.format(formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return "Invalid date-time format";
        }
    }
}