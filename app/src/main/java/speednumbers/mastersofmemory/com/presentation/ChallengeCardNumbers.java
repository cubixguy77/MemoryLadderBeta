package speednumbers.mastersofmemory.com.presentation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import speednumbers.mastersofmemory.com.domain.model.Challenge;

public class ChallengeCardNumbers extends ChallengeCard implements IChallengeCardNumbers.View {
    private Challenge challenge;
    @BindView(R.id.challengeText)
    TextView challengeText;

    public ChallengeCardNumbers(Context context) {
        super(context);
        initializeViews(context);
    }

    public ChallengeCardNumbers(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public ChallengeCardNumbers(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context);
    }

    public ChallengeCardNumbers(Context context, Challenge challenge) {
        super(context);
        this.challenge = challenge;
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_challenge_card, this);
        ButterKnife.bind(this, view);
        onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        digitGroupingContainer.setVisibility(View.GONE);
        memorizationTimerContainer.setVisibility(View.GONE);
        recallTimerContainer.setVisibility(View.GONE);
        playButton.setVisibility(View.GONE);

        challengeText.setText(challenge.getTitle());
    }

    @Override
    public void selectDigitsPerGroup(int digitsPerGroup) {

    }

    @Override
    public void deselectDigitsPerGroup(int digitsPerGroup) {

    }
}
