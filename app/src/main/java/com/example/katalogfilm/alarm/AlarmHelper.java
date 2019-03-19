package com.example.katalogfilm.alarm;

import android.content.Context;

public class AlarmHelper {

    private static final String OPEN_APPLICATION = "07:00";
    private static final String RELEASE_MOVIE = "08:00";


    public static void setDailyReminder(Context context, boolean active) {
        if (active) {
            new AlarmService().setRepeatingAlarm(context, AlarmService.TYPE_OPEN_APP, OPEN_APPLICATION);
        } else {
            new AlarmService().cancelAlarm(context, AlarmService.TYPE_OPEN_APP);
        }
    }

    public static boolean isOpenApplication(Context context) {
        return new AlarmService().isAlarmSet(context, AlarmService.TYPE_OPEN_APP);
    }

    public static void setReleaseReminder(Context context, boolean active) {
        if (active) {
            new AlarmService().setRepeatingAlarm(context, AlarmService.TYPE_RELEASE, RELEASE_MOVIE);
        } else {
            new AlarmService().cancelAlarm(context, AlarmService.TYPE_RELEASE);
        }
    }

    public static boolean isRelease(Context context) {
        return new AlarmService().isAlarmSet(context, AlarmService.TYPE_RELEASE);
    }

}
