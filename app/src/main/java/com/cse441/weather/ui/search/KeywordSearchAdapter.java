package com.cse441.weather.ui.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cse441.weather.R;
import com.cse441.weather.data.model.Location;
import com.cse441.weather.ui.DetailedWeather.WeatherDetailActivity;

import java.util.List;
import java.util.stream.Collectors;

public class KeywordSearchAdapter extends RecyclerView.Adapter<KeywordSearchAdapter.KeywordViewHolder> {
    private static List<Location> locations;
    private static final String TAG = "KeywordSearchAdapter";
    public KeywordSearchAdapter(List<Location> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public KeywordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keyword_card, parent, false);
        return new KeywordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeywordViewHolder holder, int position) {
        String keyword = locations.get(position).getName();
        holder.keywordText.setText(keyword);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }



    public static class KeywordViewHolder extends RecyclerView.ViewHolder {
        TextView keywordText;

        public KeywordViewHolder(@NonNull View itemView) {
            super(itemView);
            keywordText = itemView.findViewById(R.id.tv_keyword);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Location location = locations.get(position);
                    Log.d(TAG, "Item clicked: " + location.getName());
                    Intent intent = new Intent(itemView.getContext(), WeatherDetailActivity.class);
                    intent.putExtra("location", location);
                    itemView.getContext().startActivity(intent);
                } else {
                    Log.d(TAG, "Invalid position: " + position);
                }
            });
        }
    }
}
