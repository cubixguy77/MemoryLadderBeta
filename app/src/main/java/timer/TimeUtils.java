package timer;

import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;

public class TimeUtils {
    private int hours;
    private int minutes;
    private int seconds;

    public TimeUtils(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        if (hours > 0)
            return  (hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds );
        else
            return (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" : "") + seconds ;
    }

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

    /* Formats string into:
     * 1h23m45s
     * 23m45s
     * 45s
     */
    static String formatIntoShortTime(int secsIn) {
        int hours = (secsIn / 3600),
            remainder = (secsIn % 3600),
            minutes = remainder / 60,
            seconds = remainder % 60;

        if (hours > 0) {
            return hours + "h" + minutes + "m" + seconds + "s";
        }

        if (minutes > 0) {
            return minutes + "m" + seconds + "s";
        }

        return seconds + "s";
    }

    public static SpannableString getMemorizationTimeText(float seconds) {
        float shrinkFactor = 0.7f;

        if (seconds < 60) {
            String s = String.format(java.util.Locale.US, "%.1f", seconds) + "s";
            SpannableString span =  new SpannableString(s);
            span.setSpan(new RelativeSizeSpan(shrinkFactor), s.length()-1, s.length(), 0); // shrink the "s"
            return span;
        }

        String s = TimeUtils.formatIntoHHMMSStruncated((int) seconds);
        SpannableString span =  new SpannableString(s);

        if (seconds >= 3600)
            span.setSpan(new RelativeSizeSpan(.6f), 0, s.length(), 0); // shrink all text to fit better
        else if (seconds >= 600)
            span.setSpan(new RelativeSizeSpan(.9f), 0, s.length(), 0); // shrink all text to fit better

        return span;

        /*
        String s = TimeUtils.formatIntoShortTime(seconds);

        SpannableString span =  new SpannableString(s);

        int hoursStringIndex = s.indexOf("h"),
            minutesStringIndex = s.indexOf("m"),
            secondsStringIndex = s.indexOf("s");

        if (hoursStringIndex > 0) {
            span.setSpan(new RelativeSizeSpan(shrinkFactor), hoursStringIndex, hoursStringIndex + 1, 0); // shrink the "h"
            span.setSpan(new RelativeSizeSpan(.6f), 0, s.length(), 0); // shrink all text to fit better
        }

        if (minutesStringIndex > 0) {
            span.setSpan(new RelativeSizeSpan(shrinkFactor), minutesStringIndex, minutesStringIndex + 1, 0); // shrink the "m"

            if (hoursStringIndex <= 0) {
                span.setSpan(new RelativeSizeSpan(.9f), 0, s.length(), 0); // shrink all text to fit better
            }
        }

        if (secondsStringIndex > 0) {
            span.setSpan(new RelativeSizeSpan(shrinkFactor), secondsStringIndex, secondsStringIndex + 1, 0); // shrink the "s"
        }

        return span;
        */
    }

    /*
    public int getHours() {
        return hours;
    }
    public void setHours(int hours) {
        this.hours = hours;
    }
    public int getMinutes() {
        return minutes;
    }
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
    public int getSeconds() {
        return seconds;
    }
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
    */
}
