package com.cse441.weather.ui.WeatherWeek;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.cse441.weather.data.source.AccuWeatherDataSource;
import com.cse441.weather.data.source.WeatherDataSource;
import com.cse441.weather.dto.ShortWeatherExchange;
import com.cse441.weather.utils.WeatherUtils;

import java.util.ArrayList;

public class WeatherWeekActivity extends AppCompatActivity {
    private RecyclerView rcvWeather;
    private WeatherWeekAdapter adapter;
    TextView txtLocation, txtTemperature;
    ImageView imgWeatherIcon;

    private WeatherNextViewModel nextViewModel;
    private WeatherDataSource dataSource;
    private ShortWeatherExchange weatherExchange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather_week);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dataSource = new AccuWeatherDataSource();

        txtLocation = findViewById(R.id.txtLocation);
        txtTemperature = findViewById(R.id.txtTempNextWeather);
        imgWeatherIcon = findViewById(R.id.imgWeatherIcon);

        rcvWeather = findViewById(R.id.rcvWeatherADay);

        adapter = new WeatherWeekAdapter(new ArrayList<>());
        rcvWeather.setLayoutManager(new LinearLayoutManager(this));
        rcvWeather.setAdapter(adapter);

        Intent intent  = getIntent();
        if(intent!= null && intent.hasExtra("weatherExchange") ){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                weatherExchange = intent.getSerializableExtra("weatherExchange", ShortWeatherExchange.class);
            }

            if(weatherExchange != null ){
                txtLocation.setText(weatherExchange.getLocationName());
                txtTemperature.setText(weatherExchange.getTemperature() + "°");
                imgWeatherIcon.setImageResource(WeatherUtils.mappingWeatherIcon(weatherExchange.getIcon()));

                setupViewModel();
            }
        }
    }

    private void setupViewModel() {
        nextViewModel = new ViewModelProvider(this, new WeatherNextViewModelFactory(this, dataSource,weatherExchange)).get(WeatherNextViewModel.class);
        nextViewModel.nextWeatherForecastsLiveData.observe(this,weatherForecasts -> {
            adapter.updateData(weatherForecasts);
        });
    }
}