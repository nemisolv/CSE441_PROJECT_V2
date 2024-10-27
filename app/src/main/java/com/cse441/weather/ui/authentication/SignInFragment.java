package com.cse441.weather.ui.authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse441.weather.R;

import java.util.Objects;

public class SignInFragment extends Fragment {

    private SignInViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        // Điều hướng tới SignUpFragment khi người dùng chọn Sign Up
        view.findViewById(R.id.btnSignUp).setOnClickListener(v -> {
            ((AuthenticationActivity) requireActivity()).navigateToSignUp();
        });

        return view;
    }
}