package com.cse441.weather.ui.favorite_location;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cse441.weather.R;
import com.cse441.weather.data.model.ingradients.DailyForecast;

import java.time.LocalDateTime;
import java.util.List;


public class FavoriteLocationAdapter extends RecyclerView.Adapter<FavoriteLocationAdapter.ViewHolder> {
    private final List<DailyForecast> dailyForecasts;


    public FavoriteLocationAdapter(List<DailyForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_location_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyForecast dailyForecast = dailyForecasts.get(position);
        holder.mTextTemperature.setText(dailyForecast.getTemperature().getMaximum().getValue() + "°C");
        holder.mTextLocation.setText(dailyForecast.getLocation().getName());
        boolean isNight = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            isNight = LocalDateTime.now().getHour() > 18 || LocalDateTime.now().getHour() < 6;
        }
        holder.mTextWeatherCondition.setText(isNight ? dailyForecast.getNight().getIconPhrase() : dailyForecast.getDay().getIconPhrase());
        Glide.with(holder.mImageWeatherIcon.getContext())
                .load(isNight ? dailyForecast.getNight().getIcon() : dailyForecast.getDay().getIcon())
                .placeholder(android.R.drawable.ic_menu_camera)
                .into(holder.mImageWeatherIcon);

    }

    @Override
    public int getItemCount() {
        return dailyForecasts.size();
    }


    static public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextTemperature, mTextLocation, mTextWeatherCondition;
        private final ImageView mImageWeatherIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextTemperature = itemView.findViewById(R.id.txt_temperature_current);
            mTextLocation = itemView.findViewById(R.id.txt_location_item);
            mTextWeatherCondition = itemView.findViewById(R.id.txt_weather_condition);
            mImageWeatherIcon = itemView.findViewById(R.id.img_weather_icon);
            mImageWeatherIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);

            itemView.setOnClickListener(v -> {
                // Handle click event
                int position = getAdapterPosition();
                Log.d("FavoriteLocationAdapter", "Clicked on item at position " + position);
            });
        }
    }


}
