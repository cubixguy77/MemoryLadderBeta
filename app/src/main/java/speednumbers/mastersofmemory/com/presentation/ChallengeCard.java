package speednumbers.mastersofmemory.com.presentation;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import speednumbers.mastersofmemory.com.domain.model.Challenge;

public abstract class ChallengeCard extends CardView implements IChallengeCard.View {
    private Challenge challenge;
    private boolean open = false;

    @BindView(R.id.playButton) Button playButton;
    @BindView(R.id.expandContractButton) ImageView expandContractButton;

    public ChallengeCard(Context context) {
        super(context, null, R.attr.cardStyle);
    }

    public ChallengeCard(Context context, Challenge challenge) {
        super(context, null, R.attr.cardStyle);
        this.challenge = challenge;
    }

    public ChallengeCard(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.cardStyle);
    }

    public ChallengeCard(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }

    @Override
    public void play(IChallengeSettingsModel model) {

    }

    abstract void onExpand();
    abstract void onContract();

    @OnClick(R.id.expandContractButton) void onArrowClicked() {
        if (!open) {
            expandContractButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_up));
            open = true;
            onExpand();
        }
        else {
            expandContractButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_down));
            open = false;
            onContract();
        }
    }
}