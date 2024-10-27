package com.cse441.weather.ui.main.next_weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cse441.weather.databinding.FragmentNextWeatherBinding;
import com.cse441.weather.dto.ShortWeatherExchange;
import com.cse441.weather.ui.main.home.HomeFragment;
import com.cse441.weather.utils.WeatherUtils;

import java.util.ArrayList;


public class NextWeatherFragment extends Fragment {

    private FragmentNextWeatherBinding binding;
    private NextWeatherViewModel nextWeatherViewModel;

    private NextWeatherAdapter adapter;
    private RecyclerView nextWeatherRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNextWeatherBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupViews();
        NextWeatherViewModelFactory factory = new NextWeatherViewModelFactory(requireActivity());
        nextWeatherViewModel = new ViewModelProvider(requireActivity(), factory).get(NextWeatherViewModel.class);

        adapter = new NextWeatherAdapter(new ArrayList<>(0));
        nextWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        nextWeatherRecyclerView.setAdapter(adapter);


        nextWeatherViewModel.getNextWeather().observe(getViewLifecycleOwner(), nextWeather -> {
            adapter = new NextWeatherAdapter(nextWeather.getDailyForecasts());
            nextWeatherRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        });

        TextView txtLocationName = binding.txtLocation;
        TextView txtTemperature = binding.txtTempNextWeather;
        ImageView imgIcon = binding.imgWeatherIcon;
        ShortWeatherExchange weatherExchange = HomeFragment.weatherExchange;
        txtLocationName.setText(weatherExchange.getLocationName());
        txtTemperature.setText(weatherExchange.getTemperature() + "°C");
        imgIcon.setImageResource(WeatherUtils.mappingWeatherIcon(weatherExchange.getIcon()));











        return root;
    }

    private void setupViews() {
        nextWeatherRecyclerView = binding.rcvWeatherADay;
        adapter = new NextWeatherAdapter(new ArrayList<>(0));
        nextWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        nextWeatherRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}