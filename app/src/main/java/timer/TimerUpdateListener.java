package timer;

public interface TimerUpdateListener {

    void onTimeUpdate(long seconds);
    void onTimeExpired();

}
