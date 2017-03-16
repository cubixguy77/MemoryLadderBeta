package playChallenge.timer;

public interface TimerUpdateListener {

    void onTimeUpdate(long seconds);
    void onTimeCountdownComplete();

}
