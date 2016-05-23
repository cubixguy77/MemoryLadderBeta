package speednumbers.mastersofmemory.com.presentation;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;

public class ChallengeListActivity extends BaseActivity implements IChallengeSelectionListener {
    @BindView(R.id.ChallengeListContainer) LinearLayout root;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_list);
        ButterKnife.bind(this);



        if (savedInstanceState == null) {
            addFragment(R.id.ChallengeListScroller, new ChallengeListFragment());
        }



        //initViews();
    }

    private void initViews()
    {
        int gameKey = 1; /* TODO Get this value from the intent bundle passed in from previous activity */




        repo.getChallengeList(gameKey, new IRepository.GetChallengesCallback() {
            @Override
            public void onChallengesLoaded(List<Challenge> challenges) {
                for (Challenge challenge : challenges) {
                    ChallengeCardNumbers card = new ChallengeCardNumbers(ChallengeListActivity.this, challenge);
                    root.addView(card);
                }
            }
        });

    }

    @Override
    public void onChallengeSelected(long challengeKey) {
        /* TODO Navigate to game play */
    }
}
