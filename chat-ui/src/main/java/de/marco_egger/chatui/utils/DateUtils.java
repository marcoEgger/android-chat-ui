package de.marco_egger.chatui.utils;

import android.content.Context;
import de.marco_egger.chatui.R;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Marco Egger
 */
public abstract class DateUtils {

    /**
     * Converts a timestamp into a human-readable string.
     *
     * @param context a context reference (translation purpose)
     * @param time    the timestamp to convert (in ms)
     * @return the human-readable string
     */
    public static String timestampToString(Context context, long time) {
        // Get some calendars to compare the timestamp
        Calendar cal = Calendar.getInstance();
        Calendar timestamp = Calendar.getInstance();
        timestamp.setTime(new Date(time));

        // Calculate if the timestamp of the last message is from today
        // The comparison will also work at dates like 1st january, because the Calendar class uses an offset internally
        // See also http://stackoverflow.com/questions/3006150/how-to-check-if-a-date-object-equals-yesterday
        if (cal.get(Calendar.YEAR) == timestamp.get(Calendar.YEAR)
                && cal.get(Calendar.DAY_OF_YEAR) == timestamp.get(Calendar.DAY_OF_YEAR)) {
            // The message was sent today -> display only time
            return String.format(
                    context.getString(R.string.time_placeholder),
                    timestamp.get(Calendar.HOUR_OF_DAY), timestamp.get(Calendar.MINUTE));
        } else {
            // Reduce calendar for 1 day (yesterday)
            cal.add(Calendar.DAY_OF_YEAR, -1);
            if (cal.get(Calendar.YEAR) == timestamp.get(Calendar.YEAR)
                    && cal.get(Calendar.DAY_OF_YEAR) == timestamp.get(Calendar.DAY_OF_YEAR)) {
                // The message was sent today -> display yesterday with time
                return String.format(
                        context.getString(R.string.yesterday_time_placeholder),
                        timestamp.get(Calendar.HOUR_OF_DAY), timestamp.get(Calendar.MINUTE));
            } else {
                // Just print the date
                return String.format(
                        context.getString(R.string.date_placeholder),
                        timestamp.get(Calendar.DAY_OF_MONTH), timestamp.get(Calendar.MONTH) + 1, timestamp.get(Calendar.YEAR));
            }
        }
    }
}
