package com.cse441.weather.ui.authentication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cse441.weather.data.model.Result;
import com.cse441.weather.data.model.User;
import com.cse441.weather.data.source.WeatherDataSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignUpViewModel extends ViewModel {
    // MutableLiveData để chứa thông tin đăng ký
    private final MutableLiveData<String> name = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<Result<User>> signUpStatus = new MutableLiveData<>(); // Thêm LiveData để theo dõi trạng thái đăng ký

    private final MutableLiveData<Boolean> emailVerificationSent = new MutableLiveData<>(); // Added this line
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>(); // Added this line

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;




    public SignUpViewModel() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance(); // Initialize Firestore

    }

    public MutableLiveData<Boolean> getEmailVerificationSent() {
        return emailVerificationSent;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Phương thức để lấy name
    public LiveData<String> getName() {
        return name;
    }

    // Phương thức để lấy email
    public LiveData<String> getEmail() {
        return email;
    }

    // Phương thức để lấy password
    public LiveData<String> getPassword() {
        return password;
    }

    // Phương thức để cập nhật name
    public void setName(String nameInput) {
        name.setValue(nameInput);
    }

    // Phương thức để cập nhật email
    public void setEmail(String emailInput) {
        email.setValue(emailInput);
    }

    // Phương thức để cập nhật password
    public void setPassword(String passwordInput) {
        password.setValue(passwordInput);
    }

    // Phương thức để lấy trạng thái đăng ký
    public LiveData<Result<User>> getSignUpStatus() {
        return signUpStatus;
    }

    // Phương thức để gọi API đăng ký
    public void signUpUser(String name,String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Create a User object to store in Firestore
                            User newUser = new User(user.getUid(), name, email);

                            // Save user information to Firestore
                            db.collection("users").document(user.getUid())
                                    .set(newUser)
                                    .addOnSuccessListener(aVoid -> {
                                        sendVerificationEmail(user);
                                        signUpStatus.postValue(Result.success(newUser)); // Notify success with user info
                                    })
                                    .addOnFailureListener(e -> {
                                        signUpStatus.postValue(Result.failure(new Exception("Failed to save user information.")));
                                    });
                        }
                    } else {
                        signUpStatus.postValue(Result.failure(new Exception(task.getException().getMessage())));
                    }
                });
    }

    private void sendVerificationEmail(FirebaseUser user) {
        user.sendEmailVerification().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                emailVerificationSent.postValue(true);
            } else {
                errorMessage.postValue("Failed to send verification email.");
            }
        });
    }


}
