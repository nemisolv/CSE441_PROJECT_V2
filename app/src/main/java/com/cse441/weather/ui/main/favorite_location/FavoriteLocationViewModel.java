package com.cse441.weather.ui.main.favorite_location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoriteLocationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FavoriteLocationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}