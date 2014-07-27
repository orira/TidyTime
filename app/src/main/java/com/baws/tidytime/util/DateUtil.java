package com.baws.tidytime.util;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wadereweti on 9/07/14.
 */
public class DateUtil {

    private static SimpleDateFormat formatter = new SimpleDateFormat("EEEE d MMMM");

    public static Date getCurrentDate() {
        return new Date();
    }

    public static String getToday(Context context) {
        Date today = Calendar.getInstance().getTime();
        String date = formatter.format(today);

        return date;
    }

    public static String formatDate(Date date) {
        return formatter.format(date);
    }

    public static Date getFormattedDate(String dateString) {
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
