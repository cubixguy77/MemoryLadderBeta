package timer;

import android.content.res.Resources;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;

import java.util.Locale;

import speednumbers.mastersofmemory.com.presentation.R;

/**
 * A set of static utility methods for formatting time
 */
public class TimeFormat {

   /**
    * @param secsIn The number of seconds
    * @return The time given formatted into HH:MM:SS
    */
    static String formatIntoHHMMSStruncated(long secsIn) {
        int hours = (int) (secsIn / 3600),
                remainder = (int) (secsIn % 3600),
                minutes = remainder / 60,
                seconds = remainder % 60;
        if (hours > 0)
            return  (hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds );
        else
            return (minutes < 10 ? minutes : minutes) + ":" + (seconds < 10 ? "0" : "") + seconds ;
    }

    /**
     * @param secsIn The number of seconds
     * @param resources The resources object (for access to translations)
     * @return The time given formatted into words
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

    public static int getHours(int numSeconds) {
        return numSeconds / 3600;
    }

    public static int getMinutes(int numSeconds) {
        return (numSeconds % 3600) / 60;
    }

    public static int getSeconds(int numSeconds) {
        return (numSeconds % 3600) % 60;
    }

    public static SpannableString getMemorizationTimeText(float seconds) {
        float shrinkFactor = 0.7f;

        if (seconds < 60) {
            String s = String.format(java.util.Locale.US, "%.1f", seconds) + "s";
            SpannableString span =  new SpannableString(s);
            span.setSpan(new RelativeSizeSpan(shrinkFactor), s.length()-1, s.length(), 0); // shrink the "s"
            return span;
        }

        String s = TimeFormat.formatIntoHHMMSStruncated((int) seconds);
        SpannableString span =  new SpannableString(s);

        if (seconds >= 3600)
            span.setSpan(new RelativeSizeSpan(.6f), 0, s.length(), 0); // shrink all text to fit better
        else if (seconds >= 600)
            span.setSpan(new RelativeSizeSpan(.9f), 0, s.length(), 0); // shrink all text to fit better

        return span;
    }
}
