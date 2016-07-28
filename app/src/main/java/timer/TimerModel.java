package timer;

public class TimerModel {

    CountDirection countDirection;
    boolean timeLimited;
    long timeLimitInSeconds;
    boolean timerEnabled;

    public TimerModel(CountDirection countDirection, boolean timeLimited, long timeLimitInSeconds, boolean timerEnabled) {
        this.countDirection = countDirection;
        this.timeLimited = timeLimited;
        this.timeLimitInSeconds = timeLimitInSeconds;
        this.timerEnabled = timerEnabled;
    }

}
