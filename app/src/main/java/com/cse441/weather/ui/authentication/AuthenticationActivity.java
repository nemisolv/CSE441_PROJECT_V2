package com.cse441.weather.ui.authentication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.cse441.weather.R;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        // Hiển thị SignInFragment khi mở Activity
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SignInFragment())
                    .commit();
        }
        hideToolbar();
    }

    // Điều hướng giữa SignInFragment và SignUpFragment
    public void navigateToSignUp() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SignUpFragment())
                .addToBackStack(null)
                .commit();
    }

    // hidden toolbar
    public void hideToolbar() {
        getSupportActionBar().hide();
    }

    public void navigateToSignIn() {
        getSupportFragmentManager().popBackStack();
    }
}