package com.cse441.weather.ui.favorite_location;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cse441.weather.R;
import com.cse441.weather.data.model.Location;
import com.cse441.weather.dto.LocationExchange;
import com.cse441.weather.ui.authentication.AuthenticationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AddFavoriteLocationActivity extends AppCompatActivity {

      private FirebaseAuth mAuth;
      private FirebaseFirestore db;
      private CollectionReference favoriteLocationsRef;
      private RecyclerView recyclerViewFavoriteLocations;

      private List<Location> favoriteLocations;
      private FavoriteLocationViewModel viewModel;

      private FavoriteLocationAdapter adapter;





    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_favorite_location);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        intFirebase();
        loadFavoriteLocations();
        recyclerViewFavoriteLocations = findViewById(R.id.recyclerViewFavoriteLocations);
        recyclerViewFavoriteLocations.setLayoutManager(new LinearLayoutManager(this));
        if(mAuth.getCurrentUser() == null){
            navigateToSignIn();
            finish();

        }else {
            Intent intentReceive = getIntent();
            Location location = (Location) intentReceive.getSerializableExtra("location");
            if(location!= null) {
                Log.d("AddFavoriteLocationActivity", "Location: " + location);
                // Add location to favorite
                // before check exist
                boolean isExist = false;
                for (Location favoriteLocation : favoriteLocations) {
                    if (favoriteLocation.getKey().equals(location.getKey())) {
                        isExist = true;
                        break;
                    }
                }
                if(!isExist){
                    location.setUserId(mAuth.getCurrentUser().getUid());
                    favoriteLocations.add(location);
                    favoriteLocationsRef.add(location);
                }
            }
        }


        FavoriteLocationViewModelFactory factory = new FavoriteLocationViewModelFactory(this,favoriteLocations);
        viewModel = new ViewModelProvider(this, factory).get(FavoriteLocationViewModel.class);
        adapter = new FavoriteLocationAdapter(new ArrayList<>());

        recyclerViewFavoriteLocations.setAdapter(adapter);




        viewModel.getListMutableLiveData().observe(this, weatherForecasts -> {
           if(weatherForecasts!= null && !weatherForecasts.isEmpty()) {
               FavoriteLocationAdapter adapter = new FavoriteLocationAdapter(weatherForecasts.get(0).getDailyForecasts());
               recyclerViewFavoriteLocations.setAdapter(adapter);
               adapter.notifyDataSetChanged();
           }
        });


    }



    private void loadFavoriteLocations() {
        favoriteLocations = new ArrayList<>();
     favoriteLocationsRef.whereEqualTo("userId", mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(queryDocumentSnapshots -> {
         List<LocationExchange> objects = queryDocumentSnapshots.toObjects(LocationExchange.class);
         objects.forEach(locationExchange -> {
             Location location = new Location();
             location.setKey(locationExchange.getKey());
             location.setName(locationExchange.getName());
             location.setCountry(new Location.Country(locationExchange.getCountry().getId()));
             location.setUserId(mAuth.getCurrentUser().getUid());
             favoriteLocations.add(location);
         });

      });
    }

    private void intFirebase() {
        db = FirebaseFirestore.getInstance();
        favoriteLocationsRef = db.collection("favorite_locations");
    }


    private void navigateToSignIn() {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        Intent intentReceive = getIntent();
        Location location = (Location) intentReceive.getSerializableExtra("location");
        intent.putExtra("redirect_to", AddFavoriteLocationActivity.class.getName());
        intent.putExtra("location", location);
        startActivity(intent);
    }


}