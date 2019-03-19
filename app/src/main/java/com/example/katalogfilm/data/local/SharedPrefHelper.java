package com.example.katalogfilm.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {

    private final String MOVIE_CATALOGUE = "movie";
    public static final String FLAG = "flag";
    public static final String OPENED_APP = "dailyOpened";
    public static final String RELEASED_MOVIE = "releasedMovie";

    private SharedPreferences mPreferences;

    public SharedPrefHelper(Context context) {
        mPreferences = context.getSharedPreferences(MOVIE_CATALOGUE, Context.MODE_PRIVATE);
    }

    public boolean getSettingLanguage(String key) {
        return mPreferences.getBoolean(key, false);
    }

    public void setSettingLanguage(String key, boolean value) {
        mPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getDailyNotification() {
        return mPreferences.getBoolean(OPENED_APP, true);
    }

    public void setDailyNotification(boolean value) {
        mPreferences.edit().putBoolean(OPENED_APP, value).apply();
    }

    public boolean getReleaseNotification() {
        return mPreferences.getBoolean(RELEASED_MOVIE, true);
    }

    public void setReleaseNotification(boolean value) {
        mPreferences.edit().putBoolean(RELEASED_MOVIE, value).apply();
    }


}
