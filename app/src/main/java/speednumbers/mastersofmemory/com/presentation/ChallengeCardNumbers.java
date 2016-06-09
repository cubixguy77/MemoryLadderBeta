package speednumbers.mastersofmemory.com.presentation;

import android.content.Context;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.domain.model.NumberChallenge;

public class ChallengeCardNumbers extends ChallengeCard implements IChallengeCardNumbers.View {
    private Challenge challenge;

    @BindView(R.id.challengeText) TextView challengeText;
    @BindView(R.id.memorizationTimerContainer) MemTimerSettingView memorizationTimerContainer;
    @BindView(R.id.recallTimerContainer) RelativeLayout recallTimerContainer;
    @BindView(R.id.digitGroupingContainer) DigitsPerGroupView digitsPerGroupView;

    public ChallengeCardNumbers(Context context) {
        super(context, null, R.attr.cardStyle);
        this.challenge = new Challenge(1, 1, "Test Digits", false);
        initializeViews(context);
    }

    public ChallengeCardNumbers(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.cardStyle);
        this.challenge = new Challenge(1, 1, "Test Digits", false);
        initializeViews(context);
    }

    public ChallengeCardNumbers(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.challenge = new Challenge(1, 1, "Test Digits", false);
        initializeViews(context);
    }

    public ChallengeCardNumbers(Context context, Challenge challenge) {
        super(context, challenge);
        this.challenge = challenge;
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View view = inflater.inflate(R.layout.fragment_challenge_card, this);

        View view = LayoutInflater.from(context).inflate(R.layout.fragment_challenge_card, this);

        //ButterKnife.bind(this);
        ButterKnife.bind(this, view);
        onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin = 24;
        params.setMargins(0, 0, 0, margin);
        setLayoutParams(params);



        digitsPerGroupView.setVisibility(View.GONE);
        memorizationTimerContainer.setVisibility(View.GONE);
        recallTimerContainer.setVisibility(View.GONE);
        playButton.setVisibility(View.GONE);
        challengeText.setText(challenge.getTitle());
        digitsPerGroupView.setModel(NumberChallenge.getDigitsPerGroupSetting(challenge));
        memorizationTimerContainer.setModel(NumberChallenge.getMemTimerSetting(challenge));
        //this.setVisibility(View.VISIBLE);


    }

    @Override
    public void onExpand() {
        digitsPerGroupView.setVisibility(View.VISIBLE);
        memorizationTimerContainer.setVisibility(View.VISIBLE);
        recallTimerContainer.setVisibility(View.VISIBLE);
        playButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onContract() {
        digitsPerGroupView.setVisibility(View.GONE);
        memorizationTimerContainer.setVisibility(View.GONE);
        recallTimerContainer.setVisibility(View.GONE);
        playButton.setVisibility(View.GONE);
    }

    @Override
    public void selectDigitsPerGroup(int digitsPerGroup) {

    }

    @Override
    public void deselectDigitsPerGroup(int digitsPerGroup) {

    }
}
