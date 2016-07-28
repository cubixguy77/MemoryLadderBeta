package timer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import memorization.GameStateLifeCycle;

public class TimerView extends TextView implements TimerUpdateListener, GameStateLifeCycle {

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
    public void onLoad() {
        setModel(new TimerModel(CountDirection.DOWN, true, 10, true));
        onTimeUpdate(model.timeLimitInSeconds);
    }

    @Override
    public void onMemorizationStart() {
        start();
    }

    @Override
    public void onTimeExpired() {
        System.out.println("Time Expired!");
    }

    @Override
    public void onTransitionToRecall() {

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