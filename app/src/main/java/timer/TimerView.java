package timer;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import memorization.Bus;
import memorization.GameState;
import memorization.GameStateListener;
import memorization.SaveInstanceStateListener;
import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.domain.model.NumberChallenge;
import speednumbers.mastersofmemory.challenges.domain.model.Setting;

public class TimerView extends android.support.v7.widget.AppCompatTextView implements TimerUpdateListener, GameStateListener, SaveInstanceStateListener, TimerPlayPauseListener {

    private TimerActionListener timerActionListener;
    private TimerModel model;
    private long originalTimeLimit;
    private long timeElapsed = 0;

    public TimerView(Context context) {
        super(context);
    }

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimerView(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }

    public void setModel(TimerModel model) {
        this.model = model;
        if (model.timerEnabled) {
            timerActionListener = new Timer(model, this);
        }
        else
            setVisibility(View.GONE);
    }

    @Override
    public void onTimeUpdate(long secondsRemaining) {
        timeElapsed = originalTimeLimit - secondsRemaining;
        setText(TimeUtils.formatIntoHHMMSStruncated(model.countDirection == CountDirection.DOWN ? secondsRemaining : timeElapsed));
    }

    @Override
    public void onTimeCountdownComplete() {
        System.out.println("Time Expired!");
        Bus.result.setMemTime((int) model.timeLimitInSeconds);
        Bus.getBus().onTimeExpired();
    }

    public void init() {}

    public void subscribe() {
        Bus.getBus().subscribe(this);
    }

    @Override
    public void onLoad(Challenge challenge, final Bundle savedInstanceState) {
        final Setting timerSetting = NumberChallenge.getMemTimerSetting(challenge);
        originalTimeLimit = timerSetting.getValue();

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                TimerModel model = new TimerModel(timerSetting);

                if (model.timerEnabled) {
                    if (savedInstanceState != null) {
                        if (Bus.gameState == GameState.PRE_MEMORIZATION || Bus.gameState == GameState.MEMORIZATION) {
                            model.timeLimitInSeconds = savedInstanceState.getLong("TimerView.SecondsRemaining");
                        } else {
                            setVisibility(View.INVISIBLE);
                        }
                    }

                    setModel(model);
                    onTimeUpdate(model.timeLimitInSeconds);
                }
                else {
                    setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onMemorizationStart() {
        start();
    }

    @Override
    public void onTimeExpired() {  }

    @Override
    public void onTransitionToRecall() {
        Bus.result.setMemTime((int) this.timeElapsed);
        this.cancel();

        if (getVisibility() == View.VISIBLE)  {
            setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onRecallComplete(Result result) {}

    @Override
    public void onPlayAgain() {}

    @Override
    public void onShutdown() {
        this.cancel();
    }

    public void start()  {
        /* This cannot be called immediately because, on restoring from orientation change, the timer may not have been initialized yet */
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (timerActionListener != null) {
                    timerActionListener.startTimer();
                }
            }
        });
    }

    public void pause()  {
        if (timerActionListener != null) {
            timerActionListener.pauseTimer();
        }
    }

    public void cancel() {
        if (timerActionListener != null) {
            timerActionListener.cancelTimer();
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle inState) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (timerActionListener != null) {
            outState.putLong("TimerView.SecondsRemaining", timerActionListener.getSecondsRemaining());
        }
    }

    @Override
    public void startTimer() {
        this.start();
    }

    @Override
    public void pauseTimer() {
        this.pause();
    }

    @Override
    public void stopTimer() {
        this.cancel();
    }
}