package speednumbers.mastersofmemory.challenges.presentation.activities;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import injection.components.ChallengeComponent;
import injection.modules.ChallengeModule;
import speednumbers.mastersofmemory.challenges.presentation.IAddChallengeListener;
import speednumbers.mastersofmemory.challenges.presentation.IChallengeSelectionListener;
import speednumbers.mastersofmemory.challenges.presentation.activities.BaseActivity;
import speednumbers.mastersofmemory.challenges.presentation.fragments.ChallengeListFragment;
import speednumbers.mastersofmemory.com.presentation.R;
import injection.components.DaggerChallengeComponent;

public class ChallengeListActivity extends BaseActivity implements IChallengeSelectionListener {
    private ChallengeComponent challengeComponent;

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
        this.challengeComponent = DaggerChallengeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .challengeModule(new ChallengeModule(1))
                .build();
    }

    @Override
    public ChallengeComponent getComponent() {
        return challengeComponent;
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
