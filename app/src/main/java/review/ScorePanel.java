package review;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
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
import timer.TimeUtils;

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
        memTimeText.setText(getMemorizationTimeText(result.getMemTime()));

        this.setVisibility(View.VISIBLE);
    }

    private SpannableString getMemorizationTimeText(int seconds) {
        String s = TimeUtils.formatIntoShortTime(seconds);
        SpannableString span =  new SpannableString(s);

        int hoursStringIndex = s.indexOf("h"),
            minutesStringIndex = s.indexOf("m"),
            secondsStringIndex = s.indexOf("s");

        float shrinkSize = .5f;

        if (hoursStringIndex > 0)   span.setSpan(new RelativeSizeSpan(shrinkSize), hoursStringIndex,   hoursStringIndex+1, 0);
        if (minutesStringIndex > 0) span.setSpan(new RelativeSizeSpan(shrinkSize), minutesStringIndex, minutesStringIndex+1, 0);
        if (secondsStringIndex > 0) span.setSpan(new RelativeSizeSpan(shrinkSize), secondsStringIndex, secondsStringIndex+1, 0);

        if (hoursStringIndex > 0)
            span.setSpan(new RelativeSizeSpan(.7f), 0, s.length(), 0);

        return span;
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
