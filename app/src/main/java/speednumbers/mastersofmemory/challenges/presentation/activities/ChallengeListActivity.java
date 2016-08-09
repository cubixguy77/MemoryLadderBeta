package speednumbers.mastersofmemory.challenges.presentation.activities;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import injection.components.ChallengeListComponent;
import injection.components.DaggerChallengeListComponent;
import injection.modules.ChallengeListModule;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.presentation.IAddChallengeListener;
import speednumbers.mastersofmemory.challenges.presentation.IChallengeSelectionListener;
import speednumbers.mastersofmemory.challenges.presentation.fragments.ChallengeListFragment;
import speednumbers.mastersofmemory.com.presentation.R;

public class ChallengeListActivity extends BaseActivity implements IChallengeSelectionListener {
    private ChallengeListComponent challengeListComponent;
    private long gameKey = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_list);
        ButterKnife.bind(this);
        this.initializeInjector();

        if (savedInstanceState == null) {
            addFragment(R.id.ChallengeListScroller, new ChallengeListFragment());
        }
    }

    private void initializeInjector() {
        this.challengeListComponent = DaggerChallengeListComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .challengeListModule(new ChallengeListModule(gameKey))
                .build();
    }

    @Override
    public ChallengeListComponent getComponent() {
        return challengeListComponent;
    }

    @Override
    public void onChallengeSelected(Challenge challenge) {
        System.out.println("Navigating to hte following challenge gameplay");
        System.out.println(challenge.toString());
    }

    @OnClick(R.id.AddChallengeFAB) void onAddChallenge() {
        System.out.println("Challenge add clicked");
        IAddChallengeListener challengeListFragment = (ChallengeListFragment) getSupportFragmentManager().findFragmentByTag("ChallengeListFragment");
        challengeListFragment.onChallengeAdd();
    }
}
