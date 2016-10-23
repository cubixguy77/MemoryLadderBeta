package speednumbers.mastersofmemory.challenges.settings.dialogs;

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

    public static String formatIntoEnglishTime(int secsIn) {
        int hours = (secsIn / 3600),
        remainder = (secsIn % 3600),
        minutes = remainder / 60,
        seconds = remainder % 60;

        StringBuilder sb = new StringBuilder();

        if (hours > 0) {
            if (hours == 1)
                sb.append(String.format("%d hour", 1));
            else
                sb.append(String.format("%d hours", hours));
            if (minutes > 0 || seconds > 0)
                sb.append(", ");
        }

        if (minutes > 0) {
            if (minutes == 1)
                sb.append(String.format("%d minute", 1));
            else
                sb.append(String.format("%d minutes", minutes));

            if (seconds > 0)
                sb.append(", ");
        }

        if (seconds > 0) {
            if (seconds == 1)
                sb.append(String.format("%d second", 1));
            else
                sb.append(String.format("%d seconds", seconds));
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
