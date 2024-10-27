package com.cse441.weather.utils;

import static com.cse441.weather.utils.Constants.PREFS_NAME;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class StorageUtils {


    // Method to store key-value pairs in SharedPreferences
    public static void storeKeyValue(Context context, Map<String, String> memories) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Iterate through the map and store each key-value pair
        for (Map.Entry<String, String> entry : memories.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }

        // Apply changes
        editor.apply();
    }

    // Method to retrieve a value by key
    public static String getValueByKey(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null); // Return value or null if not found
    }
}
