package playChallenge.timer;

interface TimerActionListener {
    void startTimer();
    void pauseTimer();
    void cancelTimer();
    long getSecondsRemaining();
}


