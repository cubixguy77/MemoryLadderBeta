package playChallenge.timer;


public interface TimerActionListener {

    void startTimer();
    void pauseTimer();
    void cancelTimer();
    long getSecondsRemaining();
}


