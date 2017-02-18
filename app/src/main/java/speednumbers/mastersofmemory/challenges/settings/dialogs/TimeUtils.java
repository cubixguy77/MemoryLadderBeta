package speednumbers.mastersofmemory.challenges.settings.dialogs;

import android.content.res.Resources;

import java.util.Locale;

import speednumbers.mastersofmemory.com.presentation.R;

public class TimeUtils {

    /*
    public static String formatIntoHHMMSStruncated(long secsIn) {
        int hours = (int) (secsIn / 3600),
                remainder = (int) (secsIn % 3600),
                minutes = remainder / 60,
                seconds = remainder % 60;
        if (hours > 0)
            return  (hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds );
        else
            return (minutes < 10 ? "0"+minutes : minutes) + ":" + (seconds < 10 ? "0" : "") + seconds ;
    }

    public static String formatIntoHHMMSS(long secsIn) {
        int hours = (int) (secsIn / 3600),
                remainder = (int) (secsIn % 3600),
                minutes = remainder / 60,
                seconds = remainder % 60;
        return  "0" + hours + ":" + (minutes < 10 ? "0"+minutes : minutes) + ":" + (seconds < 10 ? "0"+seconds : seconds);
    }
    */

    public static String formatIntoEnglishTime(int secsIn, Resources resources) {
        int hours = (secsIn / 3600),
        remainder = (secsIn % 3600),
        minutes = remainder / 60,
        seconds = remainder % 60;

        String secondsString = resources.getString(R.string.seconds);
        String minutesString = resources.getString(R.string.minutes);
        String hoursString = resources.getString(R.string.hours);

        StringBuilder sb = new StringBuilder();

        if (hours > 0) {
            if (hours == 1)
                sb.append(String.format(Locale.getDefault(), "%d %s", 1, hoursString));
            else
                sb.append(String.format(Locale.getDefault(), "%d %s", hours, hoursString));
            if (minutes > 0 || seconds > 0)
                sb.append(", ");
        }

        if (minutes > 0) {
            if (minutes == 1)
                sb.append(String.format(Locale.getDefault(), "%d %s", 1, minutesString));
            else
                sb.append(String.format(Locale.getDefault(), "%d %s", minutes, minutesString));

            if (seconds > 0)
                sb.append(", ");
        }

        if (seconds > 0) {
            if (seconds == 1)
                sb.append(String.format(Locale.getDefault(), "%d %s", 1, secondsString));
            else
                sb.append(String.format(Locale.getDefault(), "%d %s", seconds, secondsString));
        }

        return sb.toString();
    }

    static int getHours(int numSeconds) {
        return numSeconds / 3600;
    }

    static int getMinutes(int numSeconds) {
        return (numSeconds % 3600) / 60;
    }

    static int getSeconds(int numSeconds) {
        return (numSeconds % 3600) % 60;
    }
}
