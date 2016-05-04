package speednumbers.mastersofmemory.com.memoryladder_speednumbers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    }
}
