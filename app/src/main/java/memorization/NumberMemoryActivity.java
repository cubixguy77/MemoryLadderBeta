package memorization;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import injection.components.ChallengeComponent;
import injection.components.DaggerChallengeComponent;
import injection.modules.ChallengeModule;
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
    @BindView(R.id.keyboard_layout) TableLayout keyboard;

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





    /////////// Recall Keyboard /////////////////
    @OnClick(R.id.backSpaceButton) void onBackSpaceClicked() { Bus.getBus().onBackSpace(); }
    @OnClick(R.id.nextRowButton) void onNextRowClick() { Bus.getBus().onNextRow(); }
    @OnClick(R.id.submitRowButton) void onSubmitRowClick() {
        Bus.getBus().onSubmitRow();
    }
    @OnClick(R.id.key_1) void on1Clicked() { Bus.getBus().onKeyPress(1); }
    @OnClick(R.id.key_2) void on2Clicked() { Bus.getBus().onKeyPress(2); }
    @OnClick(R.id.key_3) void on3Clicked() { Bus.getBus().onKeyPress(3); }
    @OnClick(R.id.key_4) void on4Clicked() { Bus.getBus().onKeyPress(4); }
    @OnClick(R.id.key_5) void on5Clicked() { Bus.getBus().onKeyPress(5); }
    @OnClick(R.id.key_6) void on6Clicked() { Bus.getBus().onKeyPress(6); }
    @OnClick(R.id.key_7) void on7Clicked() { Bus.getBus().onKeyPress(7); }
    @OnClick(R.id.key_8) void on8Clicked() { Bus.getBus().onKeyPress(8); }
    @OnClick(R.id.key_9) void on9Clicked() { Bus.getBus().onKeyPress(9); }
    @OnClick(R.id.key_0) void on0Clicked() { Bus.getBus().onKeyPress(0); }







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
        keyboard.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRecallComplete(Result result) {
        keyboard.setVisibility(View.GONE);

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
