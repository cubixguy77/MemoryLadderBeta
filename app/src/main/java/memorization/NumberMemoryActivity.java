package memorization;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import injection.components.ChallengeComponent;
import injection.components.DaggerChallengeComponent;
import injection.modules.ChallengeModule;
import speednumbers.mastersofmemory.challenges.domain.interactors.GetChallengeInteractor;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.presentation.activities.BaseActivityChallenge;
import speednumbers.mastersofmemory.com.presentation.R;
import timer.TimerView;

public class NumberMemoryActivity extends BaseActivityChallenge implements GameStateListener, GetChallengeInteractor.Callback {

    @BindView(R.id.numberGrid) NumberGridView grid;
    @BindView(R.id.timerView)  TimerView timer;
    @BindView(R.id.nextGroupButton) ImageButton nextGroupButton;
    @BindView(R.id.floatingRecallMenu) LinearLayout floatingRecallMenu;

    private long challengeKey = 2;
    private ChallengeComponent challengeComponent;
    private GameStateDispatch gameStateDispatch;
    private boolean started = false;
    @Inject public GetChallengeInteractor getChallengeInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_numbers);
        ButterKnife.bind(this);
        initializeInjector();
        challengeComponent.inject(this);

        gameStateDispatch = new GameStateDispatch();
        grid.setGameStateLifeCycleListener(gameStateDispatch);
        timer.setGameStateLifeCycleListener(gameStateDispatch);
        gameStateDispatch.subscribe(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getChallengeInteractor.setCallback(this);
        getChallengeInteractor.execute();
    }

    @Override
    protected void onResume() { super.onResume();  }

    @Override
    protected void onPause() { super.onPause();  }

    @Override
    protected void onStop() { super.onStop();  }

    @Override
    protected void onDestroy() { super.onDestroy();  }


    @OnClick(R.id.nextGroupButton) void onNextClick() {
        if (!started) {
            started = true;
            nextGroupButton.setImageResource(R.drawable.ic_arrow_right);

            gameStateDispatch.onMemorizationStart();

            return;
        }

        grid.onNextClick();
    }

    @OnClick(R.id.nextRowButton) void onNextRowClick() {
        gameStateDispatch.onNextRow();
    }

    @OnClick(R.id.submitRowButton) void onSubmitRowClick() {
        gameStateDispatch.onSubmitRow();
    }



    @Override
    public void onLoad(Challenge challenge) {
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
        timer.setVisibility(View.GONE);
        nextGroupButton.setVisibility(View.GONE);
        floatingRecallMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNextRow() {
        //grid.onNextRow();
    }

    @Override
    public void onSubmitRow() {
        //grid.onSubmitRow();
    }

    @Override
    public void onChallengeLoaded(Challenge challenge) {
        gameStateDispatch.onLoad(challenge);
    }


    private void initializeInjector() {
        this.challengeComponent = DaggerChallengeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .challengeModule(new ChallengeModule(challengeKey))
                .build();
    }

    @Override
    public ChallengeComponent getComponent() {
        return challengeComponent;
    }
}
