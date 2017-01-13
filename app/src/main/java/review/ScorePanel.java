package review;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import memorization.Bus;
import memorization.GameState;
import memorization.GameStateListener;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.com.presentation.R;

public class ScorePanel extends LinearLayout implements GameStateListener {

    @BindView(R.id.accuracyText) TextView accuracyText;
    @BindView(R.id.scoreText) TextView scoreText;
    @BindView(R.id.memTimeText) TextView memTimeText;

    private Result result;

    public ScorePanel(Context context) {
        super(context);
    }
    public ScorePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        ButterKnife.bind(this);
    }

    public void subscribe() {
        Bus.getBus().subscribe(this);
    }

    private void showScorePanel() {

        /* Bind result model to Views */
        accuracyText.setText(result.getAccuracy() + "%");
        scoreText.setText(String.valueOf(result.getNumDigitsRecalledCorrectly()));
        memTimeText.setText(result.getMemTime() + "s");

        setVisibility(View.VISIBLE);
    }

    private void hideScorePanel() {
        setVisibility(View.GONE);
    }

    @Override
    public void onLoad(Challenge challenge, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (Bus.gameState == GameState.REVIEW) {
                result = Bus.result;
                showScorePanel();
            } else {
                hideScorePanel();
            }
        }
    }

    @Override
    public void onMemorizationStart() {}

    @Override
    public void onTimeExpired() {}

    @Override
    public void onTransitionToRecall() {  }

    @Override
    public void onRecallComplete(Result result) {
        this.result = result;
        showScorePanel();
    }

    @Override
    public void onPlayAgain() {}
}
