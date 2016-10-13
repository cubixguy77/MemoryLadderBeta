package memorization;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import injection.components.ChallengeComponent;
import injection.components.DaggerChallengeComponent;
import injection.modules.ChallengeModule;
import keyboard.NumericKeyboardView;
import review.Result;
import speednumbers.mastersofmemory.challenges.domain.interactors.GetChallengeInteractor;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.presentation.activities.BaseActivityChallenge;
import speednumbers.mastersofmemory.com.presentation.R;
import timer.TimerView;
import toolbar.ToolbarView;

public class NumberMemoryActivity extends BaseActivityChallenge implements GameStateListener, GridEvent.Memory.ViewEvents, GetChallengeInteractor.Callback {

    @BindView(R.id.numberGrid) NumberGridView grid;
    @BindView(R.id.timerView)  TimerView timer;
    @BindView(R.id.nextGroupButton) ImageButton nextGroupButton;
    @BindView(R.id.tool_bar) ToolbarView toolbar;
    @BindView(R.id.keyboard_layout) NumericKeyboardView keyboard;

    private long challengeKey;
    private ChallengeComponent challengeComponent;
    private boolean started = false;
    @Inject public GetChallengeInteractor getChallengeInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_numbers);
        ButterKnife.bind(this);
        this.challengeKey = getIntent().getLongExtra("ChallengeKey", -1);

        initializeInjector();

        toolbar.init(this);
        grid.init();
        timer.init();
        keyboard.init();
        Bus.getBus().subscribe(this);
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
    protected void onStop() {
        super.onStop();
        getChallengeInteractor.setCallback(null);
        //Bus.getBus().unsubscribeAll();
    }

    @Override
    protected void onDestroy() { super.onDestroy();  }

    @Override
    public void onBackPressed() {
        Bus.unsubscribeAll();

        if (timer != null) {
            timer.cancel();
        }

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        toolbar.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_submit_memorization) {
            if (timer != null) {
                timer.cancel();
            }

            Bus.getBus().onTransitionToRecall();
        }
        else if (id == R.id.action_submit_recall) {
            Bus.getBus().onSubmitAllRows();
        }
        else if (id == R.id.action_replay) {
            onPlayAgain();
        }
        else if (id == android.R.id.home) {
            this.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }







    ////////// Game State Life Cycle Listener ////////////

    @Override
    public void onLoad(Challenge challenge) {
    }

    @Override
    public void onMemorizationStart() {

    }

    @Override
    public void onTimeExpired() {
        //Bus.getBus().onTransitionToRecall();
    }

    @Override
    public void onTransitionToRecall() {
        timer.setVisibility(View.INVISIBLE);
        nextGroupButton.setVisibility(View.GONE);

    }

    @Override
    public void onRecallComplete(Result result) {

        /*
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FinalScoreCardFragment finalScoreCardFragment = new FinalScoreCardFragment();
        finalScoreCardFragment.setModel(result);
        ft.add(R.id.parentMemoryContainer, finalScoreCardFragment, "FinalScoreCardFragment");
        //ft.addToBackStack(null);
        ft.commit();
        */
    }

    @Override
    public void onPlayAgain() {
        Bus.unsubscribeAll();

        //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.remove(getSupportFragmentManager().findFragmentByTag("FinalScoreCardFragment"));
        //ft.commit();

        finish();
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        //recreate();
    }







    @Override
    public void onChallengeLoaded(Challenge challenge) {
        Bus.getBus().onLoad(challenge);
    }











    ///////////// Grid Navigation Buttons ///////////////

    @OnClick(R.id.nextGroupButton) void onNextClick() {
        if (!started) {
            started = true;
            nextGroupButton.setImageResource(R.drawable.ic_arrow_right);

            Bus.getBus().onMemorizationStart();

            return;
        }

        Bus.getBus().onNextMemoryCell();
    }

    @Override
    public void onDisablePrev() {

    }

    @Override
    public void onDisableNext() {
        nextGroupButton.setEnabled(false);
    }

    @Override
    public void onEnablePrev() {

    }

    @Override
    public void onEnableNext() {
        nextGroupButton.setEnabled(true);
    }





    ///////// Dependency Injection  ////////////
    private void initializeInjector() {
        this.challengeComponent = DaggerChallengeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .challengeModule(new ChallengeModule(challengeKey))
                .build();

        challengeComponent.inject(this);
    }

    @Override
    public ChallengeComponent getComponent() {
        return challengeComponent;
    }
}
