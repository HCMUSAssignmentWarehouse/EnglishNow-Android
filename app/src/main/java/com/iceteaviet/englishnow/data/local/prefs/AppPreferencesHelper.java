package com.iceteaviet.englishnow.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by Genius Doan on 27/12/2017.
 *
 * Entry point contains methods that help interacting with application's SharedPreferences
 */

public class AppPreferencesHelper implements PreferencesHelper {
    private static final String PREFS_NAME = "english_now_android";
    private static final String KEY_APP_LAUNCH_FIRST_TIME = "app_launch_first_time";

    private final SharedPreferences sharedPreferences;

    @Inject
    public AppPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void putString(String key, String value) {
        sharedPreferences.edit()
                .putString(key, value)
                .apply();
    }

    @Override
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    @Override
    public void putBoolean(String key, Boolean value) {
        sharedPreferences.edit()
                .putBoolean(key, value)
                .apply();
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public Boolean getAppLaunchFirstTime() {
        return sharedPreferences.getBoolean(KEY_APP_LAUNCH_FIRST_TIME, true);
    }

    @Override
    public void setAppLaunchFirstTime(Boolean isFirstTime) {
        sharedPreferences.edit()
                .putBoolean(KEY_APP_LAUNCH_FIRST_TIME, isFirstTime)
                .apply();
    }
}
