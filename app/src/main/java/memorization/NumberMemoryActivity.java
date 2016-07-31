package memorization;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import speednumbers.mastersofmemory.com.presentation.R;
import timer.TimerView;

public class NumberMemoryActivity extends Activity implements GameStateListener {

    @BindView(R.id.numberGrid) NumberGridView grid;
    @BindView(R.id.timerView)  TimerView timer;
    @BindView(R.id.nextGroupButton) ImageButton nextGroupButton;

    private GameStateDispatch gameStateDispatch;
    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_numbers);
        ButterKnife.bind(this);

        gameStateDispatch = new GameStateDispatch();

        grid.setGameStateLifeCycleListener(gameStateDispatch);
        timer.setGameStateLifeCycleListener(gameStateDispatch);
        gameStateDispatch.subscribe(this);

        gameStateDispatch.onLoad();
    }


    @OnClick(R.id.nextGroupButton) void onNextClick() {
        if (!started) {
            started = true;
            nextGroupButton.setImageResource(R.drawable.ic_arrow_right);

            gameStateDispatch.onMemorizationStart();

            return;
        }

        grid.onNextClick();
    }


    @Override
    public void onLoad() {
    }

    @Override
    public void onMemorizationStart() {
    }

    @Override
    public void onTimeExpired() {
        gameStateDispatch.onTransitionToRecall();
    }

    @Override
    public void onTransitionToRecall() {
        nextGroupButton.setVisibility(View.GONE);
    }
}
