package playChallenge.timer;

class Timer extends CountDownTimerPausable implements TimerActionListener {

    private TimerUpdateListener listener;
    private long secondsRemaining;
    private TimerModel model;

    Timer(TimerModel model, TimerUpdateListener timerListener)
    {
        super(model.timeLimited ? model.timeLimitInSeconds * 1000 : 100000000L, 1);
        this.model = model;
        this.secondsRemaining = model.timeLimitInSeconds;
        this.listener = timerListener;
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
    public long getSecondsRemaining() {
        return secondsRemaining;
    }

    @Override
    public void onTick(long millisUntilFinished)
    {
        listener.onTimeUpdate(millisUntilFinished);
    }

    @Override
    public void onFinish() {
        listener.onTimeCountdownComplete();
    }
}
