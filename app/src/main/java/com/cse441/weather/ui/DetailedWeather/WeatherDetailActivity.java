package com.cse441.weather.ui.DetailedWeather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cse441.weather.R;
import com.cse441.weather.data.model.Location;
import com.cse441.weather.data.model.WeatherForecast;
import com.cse441.weather.data.model.ingradients.AirAndPollen;
import com.cse441.weather.data.model.ingradients.DailyForecast;
import com.cse441.weather.databinding.FragmentHomeBinding;
import com.cse441.weather.ui.favorite_location.AddFavoriteLocationActivity;
import com.cse441.weather.ui.main.HourlyAdapter;
import com.cse441.weather.ui.main.WeatherViewModel;
import com.cse441.weather.ui.main.WeatherViewModelFactory;
import com.cse441.weather.utils.WeatherUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherDetailActivity extends AppCompatActivity {

    private WeatherDetailViewModel weatherViewModel;
    private Location location;
    private RecyclerView hourlyForecastRecyclerView;
    private HourlyAdapter hourlyAdapter;

    ImageView imgAddToFavorite, imgWeatherIcon;
    TextView txtLocationName, txtTemperature, txtTime, txtUV, txtRainProbability,
            txtAQ, txtAQDescription, txtWindSpeed, txtSunrise, txtSunset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.weather_detail_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().hide();
        setupViews();

        Intent receivedIntent = getIntent();
        if(receivedIntent!= null && receivedIntent.hasExtra("location")) {
            Location location = (Location) receivedIntent.getSerializableExtra("location");
            WeatherDetailViewModelFactory factory = new WeatherDetailViewModelFactory(this,location);
            weatherViewModel = new ViewModelProvider(this, factory).get(WeatherDetailViewModel.class);
            weatherViewModel.fetchWeatherData();
        }

        hourlyAdapter = new HourlyAdapter(new ArrayList<>(0));
        hourlyForecastRecyclerView.setAdapter(hourlyAdapter);
        hourlyForecastRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        hourlyAdapter.notifyDataSetChanged();


        imgAddToFavorite.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddFavoriteLocationActivity.class);
            startActivity(intent);
        });

        weatherViewModel.getLocation().observe(this, location -> {
            if (location != null) {
                this.location = location;
            }
        });

        weatherViewModel.getCurrentWeather().observe(this, weatherForecast -> {
            if (weatherForecast != null) {
                bindingDataIntoViews(weatherForecast);
            }
        });

        weatherViewModel.getHourlyForecast().observe(this, hourlyForecast -> {
            if (hourlyForecast != null) {
                hourlyAdapter = new HourlyAdapter(hourlyForecast);
                hourlyForecastRecyclerView.setAdapter(hourlyAdapter);
                hourlyForecastRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                hourlyAdapter.notifyDataSetChanged();

            }
        });


    }


    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    private void bindingDataIntoViews(WeatherForecast wf) {
        txtLocationName.setText(location.getName());
        DailyForecast dailyForecast = wf.getDailyForecasts().get(0);
        txtTemperature.setText(dailyForecast.getTemperature().getMaximum().getValue() + "°C");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
            // convert long to Date
            Date longtoDate = new Date(dailyForecast.getEpochDate() * 1000L);
            txtTime.setText(sdf.format(longtoDate));
        }
        List<AirAndPollen> airAndPollenList = dailyForecast.getAirAndPollen();
        AirAndPollen uv = airAndPollenList.stream().filter(a -> a.getName().equals("UVIndex")).findFirst().orElse(null);
        AirAndPollen aq = airAndPollenList.stream().filter(a -> a.getName().equals("AirQuality")).findFirst().orElse(null);
        assert uv != null;
        txtUV.setText(String.valueOf(uv.getValue())); // Convert to string
        assert aq != null;
        txtAQ.setText(String.valueOf(aq.getValue()));
        txtAQDescription.setText(aq.getCategory());
        txtRainProbability.setText(String.valueOf(dailyForecast.getDay().getRainProbability())); // Convert to string
        txtWindSpeed.setText(dailyForecast.getDay().getWind().getSpeed().getValue() + " km/h");
        // Convert epoch time to Date
        Date sunriseDate = new Date(dailyForecast.getSun().getEpochRise() * 1000L);
        Date sunsetDate = new Date(dailyForecast.getSun().getEpochSet() * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
        txtSunrise.setText(sdf.format(sunriseDate));
        txtSunset.setText(sdf.format(sunsetDate));
        imgWeatherIcon.setImageResource(WeatherUtils.mappingWeatherIcon(dailyForecast.getDay().getIcon()));
    }

    private void setupViews() {
        imgAddToFavorite = findViewById(R.id.img_add_to_favorite_detail);
        View includedLayout = findViewById(R.id.detailed_weather_forecast_detail_layout_detail);
        txtLocationName = includedLayout.findViewById(R.id.txt_location_current);
        txtTemperature = includedLayout.findViewById(R.id.txt_degrees);
        txtTime = includedLayout.findViewById(R.id.txt_time_current);
        txtUV = includedLayout.findViewById(R.id.txt_uv_current);
        txtRainProbability = includedLayout.findViewById(R.id.txt_precipitation_rain);
        txtAQ = includedLayout.findViewById(R.id.txt_aq_current);
        txtAQDescription = includedLayout.findViewById(R.id.txt_air_quality);
        txtWindSpeed = includedLayout.findViewById(R.id.txt_wind_speed_day);
        txtSunrise = includedLayout.findViewById(R.id.txt_sunrise);
        txtSunset = includedLayout.findViewById(R.id.txt_sunset);
        imgWeatherIcon = includedLayout.findViewById(R.id.img_weather_icon_detail);

        hourlyForecastRecyclerView = includedLayout.findViewById(R.id.rclv_hourly);
    }

}