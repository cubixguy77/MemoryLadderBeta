package timer;

import speednumbers.mastersofmemory.challengelist.challenge.settings.Setting;

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
        this(CountDirection.DOWN, timerSetting.getValue() > 0, timerSetting.getValue(), timerSetting.getValue() > 0);
    }
}
