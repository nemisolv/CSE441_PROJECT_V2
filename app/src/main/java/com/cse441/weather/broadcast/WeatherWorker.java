package com.cse441.weather.broadcast;// WeatherWorker.java
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cse441.weather.R;
import com.cse441.weather.ui.main.WeatherViewModel;

public class WeatherWorker extends Worker {
    private static final String channelId = "weather_notification_channel";

    public WeatherWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @NonNull
    @Override
    public Result doWork() {
        // Thực hiện gọi API lấy dữ liệu thời tiết tại đây
        // Giả lập kết quả thời tiết
        String weatherData = "Sunny, 25°C"; // Thay bằng dữ liệu từ API của


        
        // Hiển thị thông báo với dữ liệu thời tiết
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Weather Forecast")
                .setContentText("Today's weather: " + weatherData)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
      if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
    // Request the permission
    ActivityCompat.requestPermissions(
        (Activity) getApplicationContext(),
        new String[]{Manifest.permission.POST_NOTIFICATIONS},
        1001
    );
    return Result.failure();
}
        notificationManager.notify(1002, builder.build());

        return Result.success();
    }
}
