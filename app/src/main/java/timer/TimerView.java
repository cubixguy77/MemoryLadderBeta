package timer;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import memorization.Bus;
import memorization.GameState;
import memorization.GameStateListener;
import memorization.SaveInstanceStateListener;
import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.domain.model.NumberChallenge;
import speednumbers.mastersofmemory.challenges.domain.model.Setting;

public class TimerView extends TextView implements TimerUpdateListener, GameStateListener, SaveInstanceStateListener {

    private TimerActionListener timerActionListener;
    private TimerModel model;

    public TimerView(Context context) {
        super(context);
    }

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setModel(TimerModel model) {
        this.model = model;
        if (model.timerEnabled) {
            timerActionListener = new Timer(model, this);
        }
        else
            setVisibility(View.GONE);
    }

    @Override
    public void onTimeUpdate(long seconds) {
        setText(TimeModel.formatIntoHHMMSStruncated(model.countDirection == CountDirection.DOWN ? seconds : model.timeLimitInSeconds - seconds));
    }

    @Override
    public void onTimeCountdownComplete() {
        System.out.println("Time Expired!");
        Bus.getBus().onTimeExpired();
    }

    public void init() {
        Bus.getBus().subscribe(this);
    }

    @Override
    public void onLoad(Challenge challenge, final Bundle savedInstanceState) {
        final Setting timerSetting = NumberChallenge.getMemTimerSetting(challenge);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                TimerModel model = new TimerModel(timerSetting);

                if (savedInstanceState != null) {
                    if (Bus.gameState == GameState.PRE_MEMORIZATION || Bus.gameState == GameState.MEMORIZATION) {
                        model.timeLimitInSeconds = savedInstanceState.getLong("TimerView.SecondsRemaining");
                    }
                    else {
                        setVisibility(View.INVISIBLE);
                    }
                }

                setModel(model);
                onTimeUpdate(model.timeLimitInSeconds);

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
        if (getVisibility() == View.VISIBLE)  {
            setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onRecallComplete(Result result) {   }

    @Override
    public void onPlayAgain() {

    }

    public void start()  {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                timerActionListener.startTimer();
            }
        });
    }

    public void pause()  { timerActionListener.pauseTimer(); }
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
        outState.putLong("TimerView.SecondsRemaining", timerActionListener.getSecondsRemaining());
    }
}