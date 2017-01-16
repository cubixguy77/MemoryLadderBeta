package memorization;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import injection.components.ChallengeComponent;
import injection.components.DaggerChallengeComponent;
import injection.modules.ChallengeModule;
import keyboard.NumericKeyboardView;
import memorization.navigationPanel.NavigationPanel;
import review.Result;
import review.ScorePanel;
import speednumbers.mastersofmemory.challenges.domain.interactors.AddScoreInteractor;
import speednumbers.mastersofmemory.challenges.domain.interactors.GetChallengeInteractor;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.presentation.activities.BaseActivityChallenge;
import speednumbers.mastersofmemory.com.presentation.R;
import timer.TimerView;
import toolbar.ToolbarView;

public class NumberMemoryActivity extends BaseActivityChallenge implements GameStateListener, GetChallengeInteractor.Callback {

    @BindView(R.id.numberGrid) NumberGridView grid;
    @BindView(R.id.timerView)  TimerView timer;
    @BindView(R.id.navigationPanel) NavigationPanel navigationPanel;
    @BindView(R.id.tool_bar) ToolbarView toolbar;
    @BindView(R.id.keyboard_layout) NumericKeyboardView keyboard;
    @BindView(R.id.score_panel) ScorePanel scorePanel;

    private long challengeKey;
    private ChallengeComponent challengeComponent;
    @Inject public GetChallengeInteractor getChallengeInteractor;
    @Inject public AddScoreInteractor addScoreInteractor;

    private static int activityInstanceCount = 0;
    private boolean destroyActivity = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_numbers);
        ButterKnife.bind(this);
        this.challengeKey = getIntent().getLongExtra("ChallengeKey", -1);

        initializeInjector();

        if (savedInstanceState != null) {
            System.out.println("onCreate(): with restore");
            Bus.gameState = (GameState) savedInstanceState.getSerializable("GameState");
        }
        else {
            System.out.println("onCreate()");
            Bus.gameState = GameState.PRE_MEMORIZATION;
        }

        toolbar.init(this);
        grid.init();
        timer.init();
        navigationPanel.init();
        keyboard.init();
        scorePanel.init();

        activityInstanceCount++;
    }

    private void setupSubscriptions() {
        if (!Bus.getBus().hasObservers()) {
            toolbar.subscribe();
            grid.subscribe();
            timer.subscribe();
            navigationPanel.subscribe();
            keyboard.subscribe();
            scorePanel.subscribe();
            Bus.getBus().subscribe(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart()");
        setupSubscriptions();

        if (Bus.gameState == GameState.PRE_MEMORIZATION && Bus.challenge == null) {
            getChallengeInteractor.setCallback(this);
            getChallengeInteractor.execute();
        }

        /* Ths condition occurs when the user has selected to Play Again
         * We'll retain the existing Challenge rather than fetch it again for performance's sake
         */
        else if (Bus.gameState == GameState.PRE_MEMORIZATION && Bus.challenge != null) {
            this.onChallengeLoaded(Bus.challenge);
        }
    }

    /*
     * This method is called between onStart() and onPostCreate(Bundle).
     */
    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        System.out.println("onRestoreInstanceState()");
        Bus.getBus().onLoad(Bus.challenge, inState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume()");

        /* full screen mode */
        if(Build.VERSION.SDK_INT < 19){
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        }
        else {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        if (Bus.gameState == GameState.MEMORIZATION) {
            timer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause()");

        if (Bus.gameState == GameState.MEMORIZATION) {
            timer.pause();
        }
    }


    /*
     * If called, this method will occur before onStop().
     * There are no guarantees about whether it will occur before or after onPause().
     * Note that this does get called when the app moves to the background, as it may get destroyed while there
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("onSaveInstanceState()");
        outState.putSerializable("GameState", Bus.gameState);
        destroyActivity = false;
        Bus.getBus().onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop()");
        getChallengeInteractor.setCallback(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy()");

        /* If the user decides to play again, then a new activity will be placed on top of the current one
         * This means that the current activity's onStop() and onDestroy() aren't called until
         * the new activity has already loaded all the way to onResume().
         * This check will prevent us from removing the new activity's subscribers
         */
        if (activityInstanceCount <= 1) {
            System.out.println("onDestroy(unsubscribe all)");
            Bus.unsubscribeAll();
        }

        if (destroyActivity) {
            System.out.println("onDestroy(destroy bus!)");
            Bus.destroy();
        }

        activityInstanceCount--;
    }

    @Override
    public void onBackPressed() {
        destroyActivity = true;

        if (timer != null) {
            timer.cancel();
        }

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.onCreateOptionsMenu(menu, this);
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
    public void onLoad(Challenge challenge, Bundle savedInstanceState) {}

    @Override
    public void onMemorizationStart() {}

    @Override
    public void onTimeExpired() {
        Bus.getBus().onTransitionToRecall();
    }

    @Override
    public void onTransitionToRecall() {}

    @Override
    public void onRecallComplete(Result result) {

        result.setChallengeKey((int) challengeKey);

        addScoreInteractor.setResult(result);

        /*
        addScoreInteractor.setCallback(new AddScoreInteractor.Callback() {
            @Override
            public void onScoreAdded(Result result) {
                System.out.println("Hooray! Score added!");
            }
        });
        */

        addScoreInteractor.execute();

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
        System.out.println("onPlayAgain()");
        Bus.unsubscribeAll();

        /* When the activity is started anew, the old running activity has not yet been destroyed.
         * When the old activity was calling unsubscribeAll on the bus in onDestroy()
         * the new activity had already called onCreate() and generated subscribers
         * onDestroy() then revoked those subscribers
         */
        destroyActivity = false;

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
        Bus.getBus().onLoad(challenge, null);
        System.out.println("onChallengeLoaded()");
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
