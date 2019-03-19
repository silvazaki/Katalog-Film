package com.example.katalogfilm;

import android.app.Application;

import com.example.katalogfilm.alarm.AlarmHelper;
import com.example.katalogfilm.data.local.SharedPrefHelper;

/**
 * Created by User on 2/5/2019.
 */

public class App extends Application {

    private SharedPrefHelper prefHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        prefHelper = new SharedPrefHelper(getApplicationContext());

        if(!AlarmHelper.isOpenApplication(getApplicationContext())&&prefHelper.getDailyNotification()){
            AlarmHelper.setDailyReminder(getApplicationContext(), prefHelper.getDailyNotification());
        }
        if(!AlarmHelper.isRelease(getApplicationContext())&&prefHelper.getReleaseNotification()){
            AlarmHelper.setReleaseReminder(getApplicationContext(), prefHelper.getReleaseNotification());
        }

    }
}
