package com.cse441.weather.ui.main;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import com.cse441.weather.R;
import com.cse441.weather.receiver.AlarmReceiver;
import com.cse441.weather.ui.search.SearchActivity;
import com.cse441.weather.utils.Constants;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.cse441.weather.databinding.ActivityMainBinding;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_POST_NOTIFICATIONS = 1001;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private WeatherViewModel weatherViewModel;
    private MutableLiveData<Boolean> locationFetched = new MutableLiveData<>(false);

    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
//
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_favorite_locations, R.id.nav_next_days)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ImageView searchIcon = binding.appBarMain.toolbar.findViewById(R.id.icon_search);
        searchIcon.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        });


        // Initialize FusedLocationProviderClient
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, 923);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        if (checkLocationPermissions()) {
            getLastKnownLocation();
        }

        // Kiểm tra quyền POST_NOTIFICATIONS trước khi bật thông báo
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_POST_NOTIFICATIONS);
        } else {
            setDailyWeatherNotification();
        }




        // Observe locationFetched LiveData
        locationFetched.observe(this, fetched -> {
            if (fetched) {
                // Proceed with the rest of the initialization
                weatherViewModel.fetchWeatherData();
            }
        });
//        navigationView.setNavigationItemSelectedListener( item -> {
//            int id = item.getItemId();
//            if (id == R.id.nav_home) {
//                // Handle the home action
//            } else if (id == R.id.nav_next_days) {
//
//            } else if (id == R.id.nav_gallery) {
//                // Handle the gallery action
//            } else if (id == R.id.nav_slideshow) {
//                // Handle the slideshow action
//            }
//            drawer.closeDrawer(GravityCompat.START);
//            return true;
//        });



    }

    private boolean checkLocationPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            Constants.DEFAULT_LATITUDE = location.getLatitude();
            Constants.DEFAULT_LONGITUDE = location.getLongitude();
            saveLocationToPreferences(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE);
            locationFetched.setValue(true);
            weatherViewModel.fetchWeatherData();
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setDailyWeatherNotification();
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
                    // Hiển thị hộp thoại yêu cầu người dùng bật quyền trong Cài đặt
                    showPermissionSettingsDialog();
                } else {
                    Toast.makeText(this, "Không thể thiết lập thông báo vì quyền bị từ chối.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void showPermissionSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Yêu cầu quyền thông báo")
                .setMessage("Ứng dụng cần quyền thông báo để gửi thông tin thời tiết. Vui lòng cấp quyền trong Cài đặt.")
                .setPositiveButton("Đi đến Cài đặt", (dialog, which) -> {
                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }




    private void saveLocationToPreferences(double latitude, double longitude) {
        SharedPreferences sharedPreferences = getSharedPreferences("location_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("latitude", String.valueOf(latitude));
        editor.putString("longitude", String.valueOf(longitude));
        editor.apply();
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Constants.DEFAULT_LATITUDE = location.getLatitude();
            Constants.DEFAULT_LONGITUDE = location.getLongitude();
            saveLocationToPreferences( Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };













//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.nav_home) {
//            // Handle the home action
//        } else if (id == R.id.nav_next_days) {
//            // Handle the next days action
//        } else if (id == R.id.nav_gallery) {
//            // Handle the gallery action
//        } else if (id == R.id.nav_slideshow) {
//            // Handle the slideshow action
//        }
//
//        DrawerLayout drawer = binding.drawerLayout;
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void setDailyWeatherNotification() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);

        // Dữ liệu thời tiết có thể lấy từ ViewModel hoặc API
        intent.putExtra("weatherInfo", "Trời nắng, nhiệt độ 25°C"); // Thay thế bằng dữ liệu thực tế

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // Đặt báo thức lặp lại hàng ngày
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkLocationPermissions()) {
            getLastKnownLocation();
        }
    }
}