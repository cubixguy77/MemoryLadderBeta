package playChallenge.timer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import playChallenge.Bus;

public class TimerView extends android.support.v7.widget.AppCompatTextView implements ITimer.View {

    public TimerView(Context context) {
        super(context);
    }
    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public TimerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void hide() {
        setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayTime(long seconds) {
        setText(TimeFormat.formatIntoHHMMSStruncated(seconds));
    }

    public void subscribe() {
        Bus.getBus().subscribe(new TimerPresenter(this));
    }
}