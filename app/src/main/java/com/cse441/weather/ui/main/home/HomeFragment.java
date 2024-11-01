package com.cse441.weather.ui.main.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cse441.weather.R;
import com.cse441.weather.data.model.Location;
import com.cse441.weather.data.model.WeatherForecast;
import com.cse441.weather.data.model.hourly.PreviewHourlyForecast;
import com.cse441.weather.data.model.ingradients.AirAndPollen;
import com.cse441.weather.data.model.ingradients.DailyForecast;
import com.cse441.weather.databinding.FragmentHomeBinding;
import com.cse441.weather.dto.ShortWeatherExchange;
import com.cse441.weather.ui.favorite_location.AddFavoriteLocationActivity;
import com.cse441.weather.ui.main.HourlyAdapter;
import com.cse441.weather.ui.main.WeatherViewModel;
import com.cse441.weather.ui.main.WeatherViewModelFactory;
import com.cse441.weather.utils.Utility;
import com.cse441.weather.utils.WeatherUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private WeatherViewModel weatherViewModel;
    private Location location;
    private RecyclerView hourlyForecastRecyclerView;
    private HourlyAdapter hourlyAdapter;

    public static ShortWeatherExchange weatherExchange;



    ImageView imgAddToFavorite, imgWeatherIcon;
    TextView txtLocationName, txtTemperature, txtTime, txtUV, txtRainProbability,
            txtAQ, txtAQDescription, txtWindSpeed, txtSunrise, txtSunset;


    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setupViews();

        WeatherViewModelFactory factory = new WeatherViewModelFactory(requireActivity());
        weatherViewModel = new ViewModelProvider(requireActivity(), factory).get(WeatherViewModel.class);

        hourlyAdapter = new HourlyAdapter(new ArrayList<>(0));
        hourlyForecastRecyclerView.setAdapter(hourlyAdapter);
        hourlyForecastRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        hourlyAdapter.notifyDataSetChanged();


        imgAddToFavorite.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), AddFavoriteLocationActivity.class);
            intent.putExtra("location", location);
            startActivity(intent);
        });

        weatherViewModel.getLocation().observe(getViewLifecycleOwner(), location -> {
            if (location != null) {
                this.location = location;
            }
        });

        weatherViewModel.getCurrentWeather().observe(getViewLifecycleOwner(), weatherForecast -> {
            if (weatherForecast != null) {
                bindingDataIntoViews(weatherForecast);
            }
        });

        weatherViewModel.getHourlyForecast().observe(getViewLifecycleOwner(), hourlyForecast -> {
            if (hourlyForecast != null) {
                hourlyAdapter = new HourlyAdapter(hourlyForecast);
                hourlyForecastRecyclerView.setAdapter(hourlyAdapter);
                hourlyForecastRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

                hourlyAdapter.notifyDataSetChanged();

            }
        });

        return root;
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

        weatherExchange = new ShortWeatherExchange(
                location.getName(),
                location.getKey(),
                dailyForecast.getTemperature().getMaximum().getValue(),
                dailyForecast.getDay().getIcon()

        );
    }

    private void setupViews() {
        imgAddToFavorite = binding.imgAddToFavorite;
        View includedLayout = binding.getRoot().findViewById(R.id.detailed_weather_forecast_detail_layout);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}