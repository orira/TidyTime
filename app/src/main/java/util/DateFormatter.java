package util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wadereweti on 9/07/14.
 */
public class DateFormatter {

    private static SimpleDateFormat formatter = new SimpleDateFormat("EEEE d MMMM");
    public static String getToday(Context context) {
        Date today = Calendar.getInstance().getTime();
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        String formattedCurrentDate = dateFormat.format(today);

        String date = formatter.format(today);

        return date;
    }
}
