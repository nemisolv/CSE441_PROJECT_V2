package com.cse441.weather.ui.authentication;

import android.content.Intent;
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
import com.cse441.weather.ui.main.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

public class SignInFragment extends Fragment {

    private SignInViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        TextInputEditText emailEditText = view.findViewById(R.id.emailEditText);
        TextInputEditText passwordEditText = view.findViewById(R.id.passwordEditText);
        Button signInButton = view.findViewById(R.id.signInButton);
        TextView navigateToSignUpTextView = view.findViewById(R.id.navigateToSignUpTextView);

        // Xử lý sự kiện nút Sign In
        signInButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Email and password must not be empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getContext(), "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.signInUser(email, password);
        });

        // Điều hướng tới SignUpFragment khi nhấn "Don't have an account? Sign Up"
        navigateToSignUpTextView.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SignUpFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Quan sát trạng thái đăng nhập
        viewModel.getSignInStatus().observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()) {
                Toast.makeText(getContext(), "Sign in successful!", Toast.LENGTH_SHORT).show();
                Intent intent = getActivity().getIntent();
                String redirect = intent.getStringExtra("redirect_to");
                if (redirect != null) {
                    getActivity().finish();
                    try {
                        startActivity(new Intent(getContext(), Class.forName(redirect)));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();

                    }
                } else {
                    Intent intent1 = new Intent(getContext(), MainActivity.class);
                    startActivity(intent1);
                }

            } else {
                Toast.makeText(getContext(), "Sign in failed: " + result.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Quan sát thông báo lỗi đăng nhập
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
