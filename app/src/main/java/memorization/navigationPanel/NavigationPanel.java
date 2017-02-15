package memorization.navigationPanel;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class NavigationPanel extends LinearLayout implements GameStateListener, GridEvent.Navigation.NavigationView, SaveInstanceStateListener {

    @BindView(R.id.nextGroupButton) ImageButton nextGroupButton;

    public NavigationPanel(Context context) {
        super(context);
    }

    public NavigationPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        ButterKnife.bind(this);
    }

    public void subscribe() {
        Bus.getBus().subscribe(this);
    }

    @Override
    public void onLoad(Challenge challenge, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            nextGroupButton.setEnabled(savedInstanceState.getBoolean("NavigationPanel.NextGroupButton.Enabled"));

            if (savedInstanceState.getBoolean("NavigationPanel.NextGroupButton.Visible")) {
                showNavigationPanel();
            }
            else {
                hideNavigationPanel();
            }
        }
        else {
            showNavigationPanel();
        }
    }

    private void showNavigationPanel() {
        final Animation buttonReveal = AnimationUtils.loadAnimation(getContext(), R.anim.fab_show);
        buttonReveal.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { setVisibility(View.VISIBLE); }
            @Override
            public void onAnimationEnd(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        startAnimation(buttonReveal);
    }

    private void hideNavigationPanel() {
        setVisibility(View.GONE);
    }

    @OnClick(R.id.nextGroupButton) void onNextClick() {
        if (Bus.gameState == GameState.PRE_MEMORIZATION) {
            Bus.getBus().onMemorizationStart();
        }
        else {
            Bus.getBus().onNextCell();
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
    public void onMemorizationStart() {}

    @Override
    public void onTimeExpired() {}

    @Override
    public void onTransitionToRecall() {
        hideNavigationPanel();
    }

    @Override
    public void onRecallComplete(Result result) {}

    @Override
    public void onPlayAgain() {}

    @Override
    public void onShutdown() {}


    @Override
    public void onRestoreInstanceState(Bundle inState) {}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("NavigationPanel.NextGroupButton.Enabled", nextGroupButton.isEnabled());
        outState.putBoolean("NavigationPanel.NextGroupButton.Visible", getVisibility() == View.VISIBLE);
    }
}
