package memorization;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import speednumbers.mastersofmemory.com.presentation.R;
import timer.CountDirection;
import timer.TimerModel;
import timer.TimerView;

public class NumberMemoryActivity extends Activity implements GameEventListener {

    @BindView(R.id.numberGrid) NumberGridView grid;
    @BindView(R.id.timerView)  TimerView timer;
    @BindView(R.id.nextGroupButton) ImageButton nextGroupButton;

    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_numbers);
        ButterKnife.bind(this);

        grid.onLoad();
        timer.onLoad();
    }


    @OnClick(R.id.nextGroupButton) void onNextClick() {
        if (!started) {
            started = true;
            nextGroupButton.setImageResource(R.drawable.ic_arrow_right);

            grid.onMemorizationStart();
            timer.onMemorizationStart();

            return;
        }

        grid.onNextClick();
    }

    @Override
    public void onTimeExpired() {
        grid.onTimeExpired();
    }
}
