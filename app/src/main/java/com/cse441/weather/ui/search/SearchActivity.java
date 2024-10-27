package com.cse441.weather.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cse441.weather.R;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private SearchLocationWeatherViewModel searchLocationWeatherViewModel;
    private RecyclerView searchResultRecyclerView, topCityRecyclerView, neighborhoodRecyclerView;
    private SearchView searchView;
    private KeywordSearchAdapter keywordSearchAdapter;
    private LocationCityItemAdapter locationCityItemAdapter;
    private BadgeSuggestLocationAdapter badgeSuggestLocationAdapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.search_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        hideToolbar();

        setupViews();

        SearchLocationWeatherViewModelFactory factory = new SearchLocationWeatherViewModelFactory(this);
        searchLocationWeatherViewModel = new ViewModelProvider(this, factory).get(SearchLocationWeatherViewModel.class);
        searchLocationWeatherViewModel.getTopCityWeather().observe(this, topCityWeather -> {
           if(topCityWeather != null && !topCityWeather.isEmpty()) {
               locationCityItemAdapter = new LocationCityItemAdapter(topCityWeather);
               topCityRecyclerView.setAdapter(locationCityItemAdapter);
               locationCityItemAdapter.notifyDataSetChanged();
           }
        });

        searchLocationWeatherViewModel.getSearchLocations().observe(this, keywordSearch -> {
            if(keywordSearch != null && !keywordSearch.isEmpty()) {
                searchResultRecyclerView.setVisibility(View.VISIBLE);
                searchResultRecyclerView.bringToFront();
                keywordSearchAdapter = new KeywordSearchAdapter(keywordSearch);
                searchResultRecyclerView.setAdapter(keywordSearchAdapter);
                keywordSearchAdapter.notifyDataSetChanged();
            }
        });

        searchLocationWeatherViewModel.getNeighbourhoods().observe(this, badgeSuggestLocations -> {
            if(badgeSuggestLocations != null && !badgeSuggestLocations.isEmpty()) {
                badgeSuggestLocationAdapter = new BadgeSuggestLocationAdapter(badgeSuggestLocations);
                neighborhoodRecyclerView.setAdapter(badgeSuggestLocationAdapter);
                badgeSuggestLocationAdapter.notifyDataSetChanged();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query == null || query.isEmpty()) {
                    return false;
                }
                searchLocationWeatherViewModel.searchLocation(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText == null || newText.isEmpty()) {
                    return false;
                }
                searchLocationWeatherViewModel.searchLocation(newText);
                return false;
            }
        });








    }

    @SuppressLint("NotifyDataSetChanged")
    private void setupViews() {
            searchResultRecyclerView = findViewById(R.id.recyclerKeywordSuggestions);
            topCityRecyclerView = findViewById(R.id.recyclerTopCities);
        neighborhoodRecyclerView = findViewById(R.id.recyclerNeighbourhoodRegions);
            searchView = findViewById(R.id.searchViewLocation);

        topCityRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        neighborhoodRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        topCityRecyclerView.setAdapter(locationCityItemAdapter);
        searchResultRecyclerView.setAdapter(keywordSearchAdapter);
        neighborhoodRecyclerView.setAdapter(badgeSuggestLocationAdapter);




    }

    public void hideToolbar() {
        getSupportActionBar().hide();
    }
}