package com.cse441.weather.broadcast;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.cse441.weather.R;
import com.cse441.weather.ui.main.MainActivity;
import com.cse441.weather.utils.WeatherUtils;

import java.util.Objects;

public class AlarmReceiver extends BroadcastReceiver {
    final String channelId = "weather_notification_channel";

    SharedPreferences sharedPreferences;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && Objects.equals(intent.getAction(), "daily_forecast_notification")) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sharedPreferences = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE);
                String textDay = sharedPreferences.getString("textDay", null);
                String textNight = sharedPreferences.getString("textNight", null);
                int temperature = sharedPreferences.getInt("temperature", 0);
                int icon = sharedPreferences.getInt("icon", 0);
                boolean isAlarmSet = sharedPreferences.getBoolean("isAlarmSet", false);

                if (isAlarmSet) {
                    Intent intent1 = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_IMMUTABLE);

                    int hour = intent.getIntExtra("hour", 0);
                    boolean isNight = hour >= 18 || hour < 6;
                    Log.d("AlarmReceiver", "Hour: " + hour);

                    String title = isNight ? textNight : textDay;
                    NotificationChannel channel = new NotificationChannel(channelId, "Weather Forecast", NotificationManager.IMPORTANCE_HIGH);
                    channel.setDescription("Weather Forecast Channel");
                    manager.createNotificationChannel(channel);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                            .setSmallIcon(R.drawable.ic_windy)
                            .setContentTitle("Weather Forecast")
                            .setContentText(title + " " + temperature + "°C")
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setAutoCancel(true);

                    manager.notify(1001, builder.build());


                }


            }


        }

    }
}
