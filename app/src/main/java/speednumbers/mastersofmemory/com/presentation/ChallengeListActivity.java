package speednumbers.mastersofmemory.com.presentation;

import android.os.Bundle;
import butterknife.ButterKnife;
import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.presentation.injection.components.ChallengeComponent;
import speednumbers.mastersofmemory.com.presentation.injection.components.DaggerChallengeComponent;
import speednumbers.mastersofmemory.com.presentation.injection.modules.ChallengeModule;

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
}
