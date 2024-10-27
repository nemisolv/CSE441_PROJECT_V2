package com.cse441.weather.ui.search;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cse441.weather.R;
import com.cse441.weather.data.model.Location;
import com.cse441.weather.ui.DetailedWeather.WeatherDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class BadgeSuggestLocationAdapter extends RecyclerView.Adapter<BadgeSuggestLocationAdapter.ViewHolder> {
    private List<Location> mLocations;

    public BadgeSuggestLocationAdapter(List<Location> locations) {
        if (locations == null) {
            mLocations = new ArrayList<>();
        } else {
            mLocations = locations;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.badge_suggest_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Location location = mLocations.get(position);
        // Bind location data to the view holder
        holder.bindLocationData(holder, location);
        holder.mItemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), WeatherDetailActivity.class);
            intent.putExtra("location", location);
            v.getContext().startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return mLocations.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView locationName;
        private View mItemView;


        public ViewHolder(View view) {
            super(view);
            mItemView = view;
            locationName = view.findViewById(R.id.txt_badge_label_name);
        }

        public void bindLocationData(ViewHolder holder, Location location) {
            holder.locationName.setText(location.getName());
        }
    }
}
