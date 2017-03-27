package playChallenge.timer;

public interface ITimer {
    interface View {
        void displayTime(long seconds);
        void hide();
    }

    interface TimerUpdateListener {
        void onTimeUpdate(long millisRemaining, long millisElapsed);
        void onTimeCountdownComplete();
    }

    interface TimerPlayPauseListener {
        void startTimer();
        void pauseTimer();
        void cancelTimer();
    }

    interface Model {
        void startTimer();
        void pauseTimer();
        void cancelTimer();
    }
}
