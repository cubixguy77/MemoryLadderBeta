package timer;

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
            return (minutes < 10 ? "0"+minutes : minutes) + ":" + (seconds < 10 ? "0" : "") + seconds ;
    }

    /* Formats string into:
     * 1h23m45s
     * 23m45s
     * 45s
     */
    public static String formatIntoShortTime(int secsIn) {
        int hours = (int) (secsIn / 3600),
            remainder = (int) (secsIn % 3600),
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
