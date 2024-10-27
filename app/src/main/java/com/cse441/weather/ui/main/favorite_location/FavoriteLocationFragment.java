package com.cse441.weather.ui.main.favorite_location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cse441.weather.databinding.FragmentFavoriteLocationBinding;

public class FavoriteLocationFragment extends Fragment {

    private FragmentFavoriteLocationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoriteLocationViewModel favoriteLocationViewModel =
                new ViewModelProvider(this).get(FavoriteLocationViewModel.class);

        binding = FragmentFavoriteLocationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        favoriteLocationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}