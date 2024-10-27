package com.cse441.weather.ui.main.next_weather;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cse441.weather.R;
import com.cse441.weather.data.model.ingradients.DailyForecast;
import com.cse441.weather.data.model.ingradients.DayNight;
import com.cse441.weather.utils.Utility;
import com.cse441.weather.utils.WeatherUtils;

import java.time.LocalDateTime;
import java.util.List;

public class NextWeatherAdapter extends RecyclerView.Adapter<NextWeatherAdapter.WeatherNextHolder> {
    private final List<DailyForecast> weatherList;

    public NextWeatherAdapter(List<DailyForecast> weatherList) {
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public WeatherNextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_aday, parent, false);
        return new WeatherNextHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull WeatherNextHolder holder, int position) {
        DailyForecast weatherADay = weatherList.get(position);
        if(weatherADay == null){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.txtDay.setText(Utility.mapTimeToDayOfWeek(weatherADay.getDate()));
        }
        boolean isNight = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             isNight = LocalDateTime.now().getHour() >= 18;
        }

        if(isNight) {
            DayNight night = weatherADay.getNight();
            String cutShortPhrase = night.getShortPhrase().length() > 20 ? night.getShortPhrase().substring(0, 20) + "..." : night.getShortPhrase();
            holder.txtWarning.setText(cutShortPhrase);
            holder.imgWeather.setImageResource(WeatherUtils.mappingWeatherIcon(night.getIcon()));
        }else {
            DayNight day = weatherADay.getDay();
            String cutShortPhrase = day.getShortPhrase().length() > 20 ? day.getShortPhrase().substring(0, 20) + "..." : day.getShortPhrase();
            holder.txtWarning.setText(cutShortPhrase);
            holder.imgWeather.setImageResource(WeatherUtils.mappingWeatherIcon(day.getIcon()));
        }


        holder.txtDMax.setText((int)(weatherADay.getTemperature().getMaximum().getValue()) + "°");
        holder.txtDMin.setText((int)(weatherADay.getTemperature().getMinimum().getValue()) + "°");
    }

    @Override
    public int getItemCount() {
        if(weatherList != null){
            return weatherList.size();
        }
        return 0;
    }



    static class WeatherNextHolder extends RecyclerView.ViewHolder {
        private final TextView txtDay;
        private final TextView txtWarning;
        private final TextView txtDMax;
        private final TextView txtDMin;
        private final ImageView imgWeather;

        public WeatherNextHolder(@NonNull View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtWarning = itemView.findViewById(R.id.txtWarning);
            txtDMax = itemView.findViewById(R.id.txtDMax);
            txtDMin = itemView.findViewById(R.id.txtDMin);
            imgWeather = itemView.findViewById(R.id.imageWeatherIcon);
        }
    }
}
