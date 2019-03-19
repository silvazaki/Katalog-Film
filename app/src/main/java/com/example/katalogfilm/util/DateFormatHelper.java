package com.example.katalogfilm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 02/6/2019.
 */

public class DateFormatHelper {
    public static String format(String date1) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
        return dt1.format(date);
    }

    public static boolean isToday(String date) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
        String a = format(date);
        String b = dt1.format(currentTime);

        return a.equals(b);
    }
}
