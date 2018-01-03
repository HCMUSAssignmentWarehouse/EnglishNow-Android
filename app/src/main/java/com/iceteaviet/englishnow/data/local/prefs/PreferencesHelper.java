package com.iceteaviet.englishnow.data.local.prefs;

/**
 * Created by Genius Doan on 27/12/2017.
 */

public interface PreferencesHelper {
    void putString(String key, String value);

    String getString(String key, String defaultValue);

    void putBoolean(String key, Boolean value);

    Boolean getBoolean(String key, Boolean defaultValue);

    Boolean getAppLaunchFirstTime();

    void setAppLaunchFirstTime(Boolean isFirstTime);
}
