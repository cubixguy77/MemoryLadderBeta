package playChallenge.timer;

import selectChallenge.viewChallengeCard.challengeSettings.memorizationtimer.MemTimerSettingView;
import speednumbers.mastersofmemory.challengelist.challenge.settings.Setting;

class TimerSettingModel {

    CountDirection countDirection;
    boolean timeLimited;
    long timeLimitInSeconds;
    boolean timerEnabled;

    TimerSettingModel(Setting timerSetting) {
        this.timeLimitInSeconds = timerSetting.getValue();
        this.countDirection = timeLimitInSeconds == MemTimerSettingView.UNLIMITED ? CountDirection.UP : CountDirection.DOWN;
        this.timeLimited = timeLimitInSeconds != MemTimerSettingView.DISABLED && timeLimitInSeconds != MemTimerSettingView.UNLIMITED;
        this.timerEnabled = timeLimitInSeconds != MemTimerSettingView.DISABLED;
    }
}