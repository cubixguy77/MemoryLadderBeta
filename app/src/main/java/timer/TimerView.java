package timer;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import memorization.Bus;
import memorization.GameStateListener;
import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.domain.model.NumberChallenge;
import speednumbers.mastersofmemory.challenges.domain.model.Setting;

public class TimerView extends TextView implements TimerUpdateListener, GameStateListener {

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
    public void onLoad(Challenge challenge) {
        final Setting timerSetting = NumberChallenge.getMemTimerSetting(challenge);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                setModel(new TimerModel(timerSetting));
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
    public void onTransitionToRecall() {    }

    @Override
    public void onRecallComplete(Result result) {   }

    @Override
    public void onPlayAgain() {

    }

    public void start() {
        timerActionListener.startTimer();
    }
    public void pause() {
        timerActionListener.pauseTimer();
    }
    public void cancel() {
        timerActionListener.cancelTimer();
    }
}