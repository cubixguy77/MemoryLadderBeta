package playChallenge.timer;

import android.os.Bundle;
import android.support.annotation.Nullable;

class TimerModel extends CountDownTimerPausable implements ITimer.Model {

    private TimerSettingModel setting;
    private long millisElapsed;
    private long initialMillisElapsed = 0;
    private long millisRemaining;
    private ITimer.TimerUpdateListener presenter;
    private final static long MAX_TIME_MILLIS = 100000000L;

    TimerModel(TimerSettingModel setting, @Nullable Bundle savedInstance, ITimer.TimerUpdateListener presenter) {
        super(savedInstance == null ? setting.timeLimited ? setting.timeLimitInSeconds * 1000 : MAX_TIME_MILLIS : savedInstance.getLong("TimerView.MillisRemaining"), 50);
        this.setting = setting;
        this.presenter = presenter;

        restore(savedInstance);
    }

    void save(Bundle outState) {
        outState.putLong("TimerView.MillisRemaining", getMillisRemaining());
        outState.putLong("TimerView.MillisElapsed", getMillisElapsed());
    }

    private void restore(@Nullable Bundle inState) {
        if (inState == null) {
            setMillisElapsed(0);
            setMillisRemaining(setting.timeLimited ? setting.timeLimitInSeconds * 1000 : MAX_TIME_MILLIS);
        }
        else {
            initialMillisElapsed = inState.getLong("TimerView.MillisElapsed");
            setMillisElapsed(inState.getLong("TimerView.MillisElapsed"));
            setMillisRemaining(inState.getLong("TimerView.MillisRemaining"));
        }
    }

    TimerSettingModel getTimerSettingModel() {
        return setting;
    }

    long getMillisElapsed() {
        return millisElapsed;
    }

    private void setMillisElapsed(long millisElapsed) {
        this.millisElapsed = millisElapsed;
    }

    long getMillisRemaining() {
        return millisRemaining;
    }

    private void setMillisRemaining(long millisRemaining) {
        this.millisRemaining = millisRemaining;
    }


    @Override
    public void startTimer() {
        start();
    }

    @Override
    public void pauseTimer() {
        pause();
    }

    @Override
    public void cancelTimer() {
        cancel();
    }

    @Override
    public void onTick(long millisRemaining)
    {
        setMillisRemaining(millisRemaining);
        setMillisElapsed(initialMillisElapsed + getOriginalTimeLimitInMillis() - millisRemaining);
        presenter.onTimeUpdate(getMillisRemaining(), getMillisElapsed());
    }

    @Override
    public void onFinish() {
        presenter.onTimeCountdownComplete();
    }
}
