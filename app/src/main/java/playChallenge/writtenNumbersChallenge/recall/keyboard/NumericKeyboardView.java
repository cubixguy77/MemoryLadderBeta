package playChallenge.writtenNumbersChallenge.recall.keyboard;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableLayout;

import butterknife.ButterKnife;
import butterknife.OnClick;
import playChallenge.Bus;
import playChallenge.GameState;
import playChallenge.GameStateListener;
import playChallenge.writtenNumbersChallenge.review.Result;
import selectChallenge.viewChallengeCard.Challenge;
import speednumbers.mastersofmemory.com.presentation.R;

public class NumericKeyboardView extends TableLayout implements GameStateListener {
    public NumericKeyboardView(Context context) {
        super(context);
    }

    public NumericKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        ButterKnife.bind(this);
    }

    public void subscribe() {
        Bus.getBus().subscribe(this);
    }

    private void showKeyboard() {
        setVisibility(View.VISIBLE);
    }

    private void hideKeyboard() {
        setVisibility(View.GONE);
    }


    @OnClick(R.id.backSpaceButton) void onBackSpaceClicked() { Bus.getBus().onBackSpace(); }
    @OnClick(R.id.nextRowButton) void onNextRowClick() { Bus.getBus().onNextRow(); }
    @OnClick(R.id.submitRowButton) void onSubmitRowClick() {
        Bus.getBus().onSubmitRow();
    }
    @OnClick(R.id.key_1) void on1Clicked() { Bus.getBus().onKeyPress('1'); }
    @OnClick(R.id.key_2) void on2Clicked() { Bus.getBus().onKeyPress('2'); }
    @OnClick(R.id.key_3) void on3Clicked() { Bus.getBus().onKeyPress('3'); }
    @OnClick(R.id.key_4) void on4Clicked() { Bus.getBus().onKeyPress('4'); }
    @OnClick(R.id.key_5) void on5Clicked() { Bus.getBus().onKeyPress('5'); }
    @OnClick(R.id.key_6) void on6Clicked() { Bus.getBus().onKeyPress('6'); }
    @OnClick(R.id.key_7) void on7Clicked() { Bus.getBus().onKeyPress('7'); }
    @OnClick(R.id.key_8) void on8Clicked() { Bus.getBus().onKeyPress('8'); }
    @OnClick(R.id.key_9) void on9Clicked() { Bus.getBus().onKeyPress('9'); }
    @OnClick(R.id.key_0) void on0Clicked() { Bus.getBus().onKeyPress('0'); }

    @Override
    public void onLoad(Challenge challenge, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (Bus.gameState == GameState.RECALL) {
                showKeyboard();
            } else {
                hideKeyboard();
            }
        }
    }

    @Override
    public void onMemorizationStart() {}

    @Override
    public void onTimeExpired() {}

    @Override
    public void onTransitionToRecall() {
        showKeyboard();
    }

    @Override
    public void onRecallComplete(Result result) {
        hideKeyboard();
    }

    @Override
    public void onPlayAgain() {}

    @Override
    public void onShutdown() {

    }
}
