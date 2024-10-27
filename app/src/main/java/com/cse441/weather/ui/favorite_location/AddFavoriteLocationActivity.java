package com.cse441.weather.ui.favorite_location;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cse441.weather.R;
import com.cse441.weather.ui.authentication.AuthenticationActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AddFavoriteLocationActivity extends AppCompatActivity {

      private FirebaseAuth mAuth;


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

        if(mAuth.getCurrentUser() == null){
            navigateToSignIn();
            finish();

        }


    }
    private void navigateToSignIn() {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.putExtra("redirect_to", AddFavoriteLocationActivity.class.getName());
        startActivity(intent);
    }
}