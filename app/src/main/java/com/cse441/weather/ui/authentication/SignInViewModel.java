package com.cse441.weather.ui.authentication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignInViewModel extends ViewModel {

    private final MutableLiveData<String> signInStatus = new MutableLiveData<>();

    public LiveData<String> getSignInStatus() {
        return signInStatus;
    }

    public void signIn(String email, String password) {
        // Xử lý đăng nhập ở đây
        // Ví dụ: signInStatus.setValue("Success");
    }
}
