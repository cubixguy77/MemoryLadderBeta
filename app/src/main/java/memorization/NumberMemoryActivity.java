package memorization;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import injection.components.ChallengeComponent;
import injection.components.DaggerChallengeComponent;
import injection.modules.ChallengeModule;
import review.Result;
import review.ScorePanel;
import scores.AddScoreInteractor;
import scores.GetScoreListInteractor;
import scores.ScoreListFragment;
import speednumbers.mastersofmemory.challenges.domain.interactors.GetChallengeInteractor;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.presentation.activities.BaseActivityChallenge;
import speednumbers.mastersofmemory.com.presentation.R;
import toolbar.ToolbarView;

public class NumberMemoryActivity extends BaseActivityChallenge implements GameStateListener, GetChallengeInteractor.Callback {

    @BindView(R.id.toolbar) ToolbarView toolbar;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;
    ViewPagerAdapter adapter;
    @BindView(R.id.scorePanel) ScorePanel scorePanel;

    private long challengeKey;

    private ChallengeComponent challengeComponent;
    @Inject public GetChallengeInteractor getChallengeInteractor;
    @Inject public AddScoreInteractor addScoreInteractor;
    @Inject public GetScoreListInteractor getScoreListInteractor;

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
            Log.d("ML.NumberMemoryActivity", "onCreate(): with restore");
            Bus.gameState = (GameState) savedInstanceState.getSerializable("GameState");
        }
        else {
            Log.d("ML.NumberMemoryActivity", "onCreate()");
            Bus.gameState = GameState.PRE_MEMORIZATION;
        }

        toolbar.init(this);
        scorePanel.init();

        setupSubscriptions();
        setupViewPager();

        activityInstanceCount++;
    }

    private void setupSubscriptions() {
        if (!Bus.getBus().hasObservers()) {
            toolbar.subscribe();
            scorePanel.subscribe();
            Bus.getBus().subscribe(this);
        }
    }

    private void setupViewPager() {
        this.adapter = new ViewPagerAdapter(getSupportFragmentManager());

        MemoryFragment memoryFragment = new MemoryFragment();
        this.adapter.addFragment(memoryFragment, getString(R.string.tabLabel_Results));

        viewPager.setAdapter(this.adapter);
        tabLayout.setupWithViewPager(viewPager);

        if (Bus.gameState == GameState.REVIEW) {
            this.addScoreListFragment();
            tabLayout.setVisibility(View.VISIBLE);
        }
        else {
            tabLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ML.NumberMemoryActivity", "onStart()");

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
        Log.d("ML.NumberMemoryActivity", "onRestoreInstanceState()");
        Bus.getBus().onLoad(Bus.challenge, inState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ML.NumberMemoryActivity", "onResume()");

        makeFullScreen();

        if (Bus.gameState == GameState.MEMORIZATION) {
            Bus.getBus().startTimer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ML.NumberMemoryActivity", "onPause()");

        if (Bus.gameState == GameState.MEMORIZATION) {
            Bus.getBus().pauseTimer();
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
        Log.d("ML.NumberMemoryActivity", "onSaveInstanceState()");
        outState.putSerializable("GameState", Bus.gameState);
        destroyActivity = false;
        Bus.getBus().onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ML.NumberMemoryActivity", "onStop()");
        getChallengeInteractor.setCallback(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ML.NumberMemoryActivity", "onDestroy()");

        /* If the user decides to play again, then a new activity will be placed on top of the current one
         * This means that the current activity's onStop() and onDestroy() aren't called until
         * the new activity has already loaded all the way to onResume().
         * This check will prevent us from removing the new activity's subscribers
         */
        if (activityInstanceCount <= 1) {
            Log.d("ML.NumberMemoryActivity", "onDestroy(unsubscribe all)");
            Bus.unsubscribeAll();
        }

        if (destroyActivity) {
            Log.d("ML.NumberMemoryActivity", "onDestroy(destroy bus!)");
            Bus.getBus().onShutdown();
            Bus.destroy();
        }

        activityInstanceCount--;
    }

    @Override
    public void onBackPressed() {
        destroyActivity = true;
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
            Bus.getBus().onTransitionToRecall();
        }
        else if (id == R.id.action_submit_recall) {
            Bus.getBus().onSubmitAllRows();
        }
        else if (id == R.id.action_replay) {
            this.onPlayAgain();
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
        addScoreInteractor.setCallback(new AddScoreInteractor.Callback() {
            @Override
            public void onScoreAdded(Result result) {
                Log.d("ML.NumberMemoryActivity", "New Score added successfully: " + result.getNumDigitsRecalledCorrectly());

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addScoreListFragment();
                        tabLayout.setVisibility(View.VISIBLE);
                    }
                }, 1000);
            }
        });
        addScoreInteractor.execute();
    }

    private void addScoreListFragment() {
        ScoreListFragment scoreListFragment = new ScoreListFragment();
        scoreListFragment.provideDependencies(getScoreListInteractor);
        adapter.addFragment(scoreListFragment, getString(R.string.tabLabel_MyScores));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPlayAgain() {
        Log.d("ML.NumberMemoryActivity", "onPlayAgain()");
        Bus.unsubscribeAll();

        /* When the activity is started anew, the old running activity has not yet been destroyed.
         * When the old activity was calling unsubscribeAll on the bus in onDestroy()
         * the new activity had already called onCreate() and generated subscribers
         * onDestroy() then revoked those subscribers
         */
        destroyActivity = false;

        finish();
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @Override
    public void onShutdown() {}


    @Override
    public void onChallengeLoaded(final Challenge challenge) {
        Log.d("ML.NumberMemoryActivity", "onChallengeLoaded(): " + challenge.getTitle());

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Bus.getBus().onLoad(challenge, null);
            }
        }, 100);
    }










    /* full screen mode */
    private void makeFullScreen() {
        if(Build.VERSION.SDK_INT < 19){
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        }
        else {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
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
