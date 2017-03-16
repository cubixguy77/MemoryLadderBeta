package selectChallenge.viewChallengeCard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import selectChallenge.deleteChallenge.IDeleteChallengeListener;
import speednumbers.mastersofmemory.challengelist.challenge.settings.digitsource.DigitSource;
import speednumbers.mastersofmemory.challengelist.challenge.settings.digitgrouping.DigitsPerGroupView;
import speednumbers.mastersofmemory.challengelist.challenge.settings.digitsource.DigitSourceView;
import speednumbers.mastersofmemory.challengelist.challenge.settings.memorizationtimer.MemTimerSettingView;
import speednumbers.mastersofmemory.com.presentation.R;

public class ChallengeCardNumbers extends ChallengeCard implements IChallengeCard.View {
    private Challenge challenge;
    private IDeleteChallengeListener deleteChallengeListener;

    @BindView(R.id.challengeText) TextView challengeText;
    @BindView(R.id.deleteButton) ImageView deleteButton;
    @BindView(R.id.digitGroupingContainer) DigitsPerGroupView digitsPerGroupView;
    @BindView(R.id.digitSourceContainer) DigitSourceView digitSourceView;
    @BindView(R.id.memorizationTimerContainer) MemTimerSettingView memorizationTimerContainer;
    @BindView(R.id.challengeCardBottomActionContainer) LinearLayout actionContainer;

    public ChallengeCardNumbers(Context context) {
        super(context, null, R.attr.cardStyle);
    }

    public ChallengeCardNumbers(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.cardStyle);
    }

    public ChallengeCardNumbers(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ChallengeCardNumbers(Context context, Challenge challenge, IDeleteChallengeListener deleteChallengeListener) {
        super(context, challenge);
        this.challenge = challenge;
        this.deleteChallengeListener = deleteChallengeListener;
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_challenge_card, this);
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

        onContract();

        DigitSource digitSource = DigitSource.values()[NumberChallenge.getDigitSourceSetting(challenge).getValue()];
        if (digitSource == DigitSource.RANDOM)
            challengeText.setText(getResources().getString(R.string.challengeList_numDigits, NumberChallenge.getNumDigitsSetting(challenge).getValue()));
        else if (digitSource == DigitSource.PI)
            challengeText.setText(getResources().getString(R.string.challengeList_numDigits_pi, NumberChallenge.getNumDigitsSetting(challenge).getValue()));

        digitsPerGroupView.setModel(NumberChallenge.getDigitsPerGroupSetting(challenge));
        digitSourceView.setModel(NumberChallenge.getDigitSourceSetting(challenge));
        memorizationTimerContainer.setModel(NumberChallenge.getMemTimerSetting(challenge));
    }

    @OnClick(R.id.deleteButton) void onDeleteClicked() {
        delete();
    }

    @Override
    public void delete() {
        deleteChallengeListener.onDeleteChallenge(this.challenge, this);
    }

    @Override
    public void onExpand() {
        digitsPerGroupView.setVisibility(VISIBLE);
        digitSourceView.setVisibility(VISIBLE);
        memorizationTimerContainer.setVisibility(VISIBLE);
        actionContainer.setVisibility(VISIBLE);
    }

    @Override
    public void onContract() {
        digitsPerGroupView.setVisibility(GONE);
        digitSourceView.setVisibility(GONE);
        memorizationTimerContainer.setVisibility(GONE);
        actionContainer.setVisibility(GONE);
    }
}
