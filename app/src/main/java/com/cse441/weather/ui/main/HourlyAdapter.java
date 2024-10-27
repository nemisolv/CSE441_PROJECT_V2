package com.cse441.weather.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cse441.weather.R;
import com.cse441.weather.data.model.hourly.PreviewHourlyForecast;
import com.cse441.weather.utils.Utility;
import com.cse441.weather.utils.WeatherUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {
    private List<PreviewHourlyForecast> mHourlies;
    private Context mContext;

    public HourlyAdapter(List<PreviewHourlyForecast> hourlies) {
        mHourlies = hourlies;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PreviewHourlyForecast hourly = mHourlies.get(position);
        holder.bindData(holder,hourly);
//        holder.mItemView.setOnClickListener(v -> {
//            Intent intent = new Intent(mContext, WeatherDetailActivity.class);
//            intent.putExtra("location", hourly.getLocationKey());
//            mContext.startActivity(intent);
//        });

    }

    @Override
    public int getItemCount() {
        return mHourlies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final View mItemView;
        private final TextView txtTemperature, txtHour;
        private final ImageView imgWeatherIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            txtTemperature = itemView.findViewById(R.id.txt_temp);
            txtHour = itemView.findViewById(R.id.txt_hour_hourly);
            imgWeatherIcon = itemView.findViewById(R.id.img_weather);

        }

        @SuppressLint("SetTextI18n")
        public void bindData(ViewHolder holder, PreviewHourlyForecast hourly) {
            Log.d("PreviewHourlyAdapter", "bindData: " + hourly);
            txtTemperature.setText(hourly.getTemperature().getValue() + "°C");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
                Date sunriseDate = new Date(hourly.getEpochTime() * 1000L);
                txtHour.setText(sdf.format(sunriseDate));

            }
            imgWeatherIcon.setImageResource(WeatherUtils.mappingWeatherIcon(hourly.getIcon()));
        }
    }
}
