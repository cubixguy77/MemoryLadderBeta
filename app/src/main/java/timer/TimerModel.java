package timer;

import speednumbers.mastersofmemory.challenges.domain.model.Setting;

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

    public TimerModel(Setting timerSetting) {
        this(CountDirection.DOWN, true, 4, true);
    }
}
