package com.cse441.weather.ui.authentication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cse441.weather.data.model.Result;
import com.cse441.weather.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInViewModel extends ViewModel {
    // MutableLiveData để chứa thông tin đăng nhập
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<Result<User>> signInStatus = new MutableLiveData<>(); // Trạng thái đăng nhập
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>(); // Thông báo lỗi đăng nhập

    private FirebaseAuth mAuth;

    public SignInViewModel() {
        mAuth = FirebaseAuth.getInstance(); // Khởi tạo Firebase Auth
    }

    // Phương thức để lấy email
    public LiveData<String> getEmail() {
        return email;
    }

    // Phương thức để lấy password
    public LiveData<String> getPassword() {
        return password;
    }

    // Phương thức để cập nhật email
    public void setEmail(String emailInput) {
        email.setValue(emailInput);
    }

    // Phương thức để cập nhật password
    public void setPassword(String passwordInput) {
        password.setValue(passwordInput);
    }

    // Phương thức để lấy trạng thái đăng nhập
    public LiveData<Result<User>> getSignInStatus() {
        return signInStatus;
    }

    // Phương thức để lấy thông báo lỗi
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Phương thức đăng nhập
    public void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            if (firebaseUser.isEmailVerified()) {
                                User user = new User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail());
                                signInStatus.postValue(Result.success(user));
                            } else {
                                errorMessage.postValue("Email is not verified. Please verify your email.");
                                signInStatus.postValue(Result.failure(new Exception("Email not verified.")));
                            }
                        }
                    } else {
                        errorMessage.postValue("Failed to sign in: " + (task.getException() != null ? task.getException().getMessage() : "Unknown error"));
                        signInStatus.postValue(Result.failure(new Exception("Sign in failed.")));
                    }
                });
    }
}
