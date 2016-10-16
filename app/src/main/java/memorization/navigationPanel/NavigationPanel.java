package memorization.navigationPanel;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import memorization.Bus;
import memorization.GameState;
import memorization.GameStateListener;
import memorization.GridEvent;
import memorization.SaveInstanceStateListener;
import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.com.presentation.R;

public class NavigationPanel extends LinearLayout implements GameStateListener, GridEvent.Memory.ViewEvents, SaveInstanceStateListener {

    @BindView(R.id.nextGroupButton) ImageButton nextGroupButton;

    public NavigationPanel(Context context) {
        super(context);
    }

    public NavigationPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        Bus.getBus().subscribe(this);
        ButterKnife.bind(this);
    }

    @Override
    public void onLoad(Challenge challenge, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            nextGroupButton.setEnabled(savedInstanceState.getBoolean("NavigationPanel.NextGroupButton.Enabled"));
            setVisibility(savedInstanceState.getBoolean("NavigationPanel.NextGroupButton.Visible") ? View.VISIBLE : View.GONE);
        }
    }

    private void showNavigationPanel() {
        setVisibility(View.VISIBLE);
    }

    private void hideNavigationPanel() {
        setVisibility(View.GONE);
    }

    @OnClick(R.id.nextGroupButton) void onNextClick() {
        if (Bus.gameState == GameState.PRE_MEMORIZATION) {
            Bus.getBus().onMemorizationStart();
        }
        else {
            Bus.getBus().onNextMemoryCell();
        }
    }

    @Override
    public void onDisablePrev() {}
    @Override
    public void onEnablePrev() {}
    @Override
    public void onDisableNext() {
        nextGroupButton.setEnabled(false);
    }
    @Override
    public void onEnableNext() {
        nextGroupButton.setEnabled(true);
    }

    @Override
    public void onMemorizationStart() {
        showNavigationPanel();
    }

    @Override
    public void onTimeExpired() { }

    @Override
    public void onTransitionToRecall() {
        hideNavigationPanel();
    }

    @Override
    public void onRecallComplete(Result result) {}

    @Override
    public void onPlayAgain() {}



    @Override
    public void onRestoreInstanceState(Bundle inState) {}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("NavigationPanel.NextGroupButton.Enabled", nextGroupButton.isEnabled());
        outState.putBoolean("NavigationPanel.NextGroupButton.Visible", getVisibility() == View.VISIBLE);
    }
}
