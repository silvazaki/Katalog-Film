package com.example.katalogfilm.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.katalogfilm.R;
import com.example.katalogfilm.data.local.SharedPrefHelper;
import com.example.katalogfilm.data.model.MovieItems;
import com.example.katalogfilm.ui.activity.MainActivity;
import com.example.katalogfilm.util.DateFormatHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class AlarmService extends BroadcastReceiver {
    public static final String TYPE_OPEN_APP = "OpenApp";
    public static final String TYPE_RELEASE = "ReleaseMovie";
    public static final String EXTRA_TYPE = "type";

    String CHANNEL_ID = "channel_01";
    CharSequence CHANNEL_NAME = "movie channel";

    private final int ID_OPENED_APP = 200;
    private final int ID_RELEASE = 300;

    public AlarmService() {

    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        SharedPrefHelper prefHelper = new SharedPrefHelper(context);

        String type = intent.getStringExtra(EXTRA_TYPE);

        final int notifId = type.equalsIgnoreCase(TYPE_OPEN_APP) ? ID_OPENED_APP : ID_RELEASE;

        if (type.equalsIgnoreCase(TYPE_OPEN_APP)) {
            if (prefHelper.getReleaseNotification())
                sendNotification(context, context.getString(R.string.app_name), context.getString(R.string.app_name) + " is missing you", notifId);
        } else {
            if (prefHelper.getDailyNotification()) {

                AsyncHttpClient client = new AsyncHttpClient();

                final ArrayList<MovieItems> movieItemses = new ArrayList<>();
                String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=3dce2cc3191c483d42c878e6409fd560" +
                        "&language=" + "en-EN" + "";

                client.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        Gson gson = new GsonBuilder().create();
                        try {
                            JSONObject obj = new JSONObject(new String(responseBody));
                            JSONArray results = obj.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                movieItemses.add(gson.fromJson(results.getString(i), MovieItems.class));
                                if (DateFormatHelper.isToday(movieItemses.get(i).getReleaseDate())) {
                                    sendNotification(context, context.getString(R.string.app_name), "" + movieItemses.get(i).getTitle() + " was released", notifId);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        }

    }

    public void setRepeatingAlarm(Context context, String type, String time) {
        int idAlarm = type.equalsIgnoreCase(TYPE_OPEN_APP) ? ID_OPENED_APP : ID_RELEASE;

        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(EXTRA_TYPE, type);

        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idAlarm, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private void sendNotification(Context context, String title, String message, int notifId) {

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_update)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);

        /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        Materi ini akan dibahas lebih lanjut di modul extended
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(notifId, notification);
        }

    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmService.class);
        int requestCode = type.equalsIgnoreCase(TYPE_OPEN_APP) ? ID_OPENED_APP : ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public boolean isAlarmSet(Context context, String type) {
        Intent intent = new Intent(context, AlarmService.class);
        int requestCode = type.equalsIgnoreCase(TYPE_OPEN_APP) ? ID_OPENED_APP : ID_RELEASE;

        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }

    private String TIME_FORMAT = "HH:mm";

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

}
