package com.cse441.weather.ui.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cse441.weather.R;
import com.cse441.weather.data.model.Location;
import com.cse441.weather.data.model.PreviewWeather;
import com.cse441.weather.ui.DetailedWeather.WeatherDetailActivity;
import com.cse441.weather.utils.WeatherUtils;

import java.util.ArrayList;
import java.util.List;

public class LocationCityItemAdapter extends RecyclerView.Adapter<LocationCityItemAdapter.ViewHolder> {
    private final List<PreviewWeather> mWeathers;


    public LocationCityItemAdapter(List<PreviewWeather> weathers) {
        if (weathers == null) {
            mWeathers = new ArrayList<>();
        } else {
            mWeathers = weathers;
        }
    }


    public void updateData(List<PreviewWeather> weathers) {
        mWeathers.clear();
        mWeathers.addAll(weathers);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_location_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PreviewWeather weather = mWeathers.get(position);
        holder.bindData(weather);
        holder.mItemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.mItemView.getContext(), WeatherDetailActivity.class);
            Location location = new Location();
            location.setKey(weather.getKey());
            location.setName(weather.getName());
            location.setCountry(weather.getCountry());
            intent.putExtra("location", location);
            holder.mItemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return mWeathers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final View mItemView;
        private final TextView mTextTemperature, mTextLocation, mTextWeatherCondition;
        private final ImageView mImageWeatherIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;

            mTextTemperature = itemView.findViewById(R.id.txt_temperature_current);
            mTextLocation = itemView.findViewById(R.id.txt_location);
            mTextWeatherCondition = itemView.findViewById(R.id.txt_weather_condition);
            mImageWeatherIcon = itemView.findViewById(R.id.img_weather_icon);
            mImageWeatherIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @SuppressLint("SetTextI18n")
        public void bindData(PreviewWeather weather) {
            mTextTemperature.setText((int)weather.getTemperature().getMetric().getValue() + "°C");
            mTextTemperature.setTextSize(50);
            mTextLocation.setText(weather.getName());
            mTextWeatherCondition.setText(String.valueOf(weather.getText()));
            mImageWeatherIcon.setImageResource(WeatherUtils.mappingWeatherIcon(weather.getIcon()));
            mImageWeatherIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }
}
