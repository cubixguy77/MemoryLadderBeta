package speednumbers.mastersofmemory.com.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;
import speednumbers.mastersofmemory.com.storage.DatabaseHelper;
import speednumbers.mastersofmemory.com.storage.Repository;

public class ChallengeListActivity extends AppCompatActivity {
    @BindView(R.id.ChallengeListContainer) LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_list);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews()
    {
        Repository repo = new Repository(DatabaseHelper.getInstance(this));

        repo.insertChallenge(new Challenge(1, -1, "10 Digits", false, null));
        repo.insertChallenge(new Challenge(1, -1, "20 Digits", false, null));
        repo.insertChallenge(new Challenge(1, -1, "30 Digits", false, null));

        repo.getChallengeList(1, new IRepository.GetChallengesCallback() {
            @Override
            public void onChallengesLoaded(List<Challenge> challenges) {
                for (Challenge challenge : challenges) {
                    ChallengeCardNumbers card = new ChallengeCardNumbers(ChallengeListActivity.this, challenge);
                    root.addView(card);
                }
            }
        });


        /*
        ChallengeCard card = new ChallengeCard(this);
        ChallengeCardDataModel model = new ChallengeCardDataModel(15);
        card.setDataModel(model);
        root.addView(card, 0);

        ChallengeCard card2 = new ChallengeCard(this);
        ChallengeCardDataModel model2 = new ChallengeCardDataModel(20);
        card2.setDataModel(model2);
        root.addView(card2, 1);

        ChallengeCard card3 = new ChallengeCard(this);
        ChallengeCardDataModel model3 = new ChallengeCardDataModel(30);
        card3.setDataModel(model3);
        root.addView(card3, 2);
        */
    }
}
