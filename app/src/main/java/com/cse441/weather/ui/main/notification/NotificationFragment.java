package com.cse441.weather.ui.main.notification;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.cse441.weather.databinding.FragmentNotificationBinding;
import com.cse441.weather.broadcast.AlarmReceiver;

import java.util.Calendar;

public class NotificationFragment extends Fragment {

    private FragmentNotificationBinding binding;
    TextView txtAlarmTitle;
    TimePicker timePicker;
    Button btnSet, btnCancel;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private SharedPreferences sharedPreferences ;
    private SharedPreferences.Editor editor;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        sharedPreferences = getContext().getSharedPreferences("notification_prefs", Context.MODE_PRIVATE);
        View root = binding.getRoot();
        setupViews();
        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            String time = String.format("%02d:%02d", hourOfDay, minute);
            txtAlarmTitle.setText("Selected Time: " + time);
            txtAlarmTitle.setVisibility(View.VISIBLE);
        });

        btnSet.setOnClickListener(v -> {
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();
            txtAlarmTitle.setText("Alarm set to: " + hour + ":" + minute);
            txtAlarmTitle.setVisibility(View.VISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Alarm Set");
            builder.setMessage("Alarm set to: " + hour + ":" + minute);
            builder.setPositiveButton("OK", (dialog, which) -> {
                // TODO: set alarm here
                setupAlarmNotification();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                editor.putBoolean("isAlarmSet", false);
                dialog.dismiss();
            });
            builder.show();
        });

        btnCancel.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Cancel Notification Weather");
            builder.setMessage("Are you sure you want to cancel the notification?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                // TODO: cancel alarm here
                txtAlarmTitle.setVisibility(View.GONE);
                pendingIntent.cancel();
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();

        });


        return root;
    }

    private void setupAlarmNotification() {
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        // Lưu thời gian vào SharedPreferences
        saveAlarmTime(hour, minute);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.setAction("daily_forecast_notification");
        intent.putExtra("hour", hour);

        alarmManager =(AlarmManager) getSystemService(getContext(), AlarmManager.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  pendingIntent);

        txtAlarmTitle.setText("Alarm set to: " + hour + ":" + minute);
        txtAlarmTitle.setVisibility(View.VISIBLE);
    }

    private void saveAlarmTime(int hour, int minute) {
        SharedPreferences preferences = getContext().getSharedPreferences("weather_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("alarm_hour", hour);
        editor.putInt("alarm_minute", minute);
        editor.apply();
    }



    private void setupViews() {
        btnSet = binding.btnSet;
        btnCancel = binding.btnCancel;
        txtAlarmTitle = binding.txtAlarmTitle;
        timePicker = binding.timePicker;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}