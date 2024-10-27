package com.cse441.weather.ui.authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cse441.weather.R;
import com.cse441.weather.data.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpFragment extends Fragment {

    private SignUpViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        TextInputEditText nameEditText = view.findViewById(R.id.nameEditText); // Thay đổi tên biến
        TextInputEditText emailEditText = view.findViewById(R.id.emailEditText);
        TextInputEditText passwordEditText = view.findViewById(R.id.passwordEditText);
        Button signUpButton = view.findViewById(R.id.signUpButton);
        TextView navigateToSignInTextView = view.findViewById(R.id.navigateToSignInTextView);

        // Gọi API khi nhấn Sign Up
        signUpButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString(); // Thay đổi từ username thành name
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            viewModel.signUpUser(name, email, password);
        });

        // Điều hướng khi nhấn vào TextView "Already have an account? Sign In"
        navigateToSignInTextView.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SignInFragment())
                    .addToBackStack(null)
                    .commit();
        });

        viewModel.getSignUpStatus().observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()) {
                User user = result.getData();
                Toast.makeText(getContext(), "Sign up successful! A verification email has been sent.", Toast.LENGTH_SHORT).show();
                // Navigate or update UI as necessary
            } else {
                Toast.makeText(getContext(), "Sign up failed: " + result.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getEmailVerificationSent().observe(getViewLifecycleOwner(), isSent -> {
            if (isSent) {
                Toast.makeText(getContext(), "A verification email has been sent.", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
