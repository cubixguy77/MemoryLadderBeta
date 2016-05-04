package speednumbers.mastersofmemory.com.memoryladder_speednumbers;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChallengeCard extends CardView implements IChallengeCard.View {
    @BindView(R.id.challengeCard) CardView challengeCard;
    @BindView(R.id.digitGroupingContainer) RelativeLayout digitGroupingContainer;
    @BindView(R.id.vertcontainer) LinearLayout vertcontainer;
    @BindView(R.id.memorizationTimerContainer) RelativeLayout memorizationTimerContainer;
    @BindView(R.id.recallTimerContainer) RelativeLayout recallTimerContainer;
    @BindView(R.id.playButton) Button playButton;
    @BindView(R.id.downButton) ImageView downButton;
    @BindView(R.id.challengeText) TextView challengeText;

    private boolean open = false;

    public ChallengeCard(Context context) {
        super(context);
    }

    public ChallengeCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChallengeCard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @OnClick(R.id.downButton) void onArrowClicked() {
        if (!open) {
            //CardView.LayoutParams layoutParams = (CardView.LayoutParams) challengeCard.getLayoutParams();
            //challengeCard.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            downButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_up));
            digitGroupingContainer.setVisibility(View.VISIBLE);
            memorizationTimerContainer.setVisibility(View.VISIBLE);
            recallTimerContainer.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.VISIBLE);
            open = true;
        }
        else {
            downButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_down));
            digitGroupingContainer.setVisibility(View.GONE);
            memorizationTimerContainer.setVisibility(View.GONE);
            recallTimerContainer.setVisibility(View.GONE);
            playButton.setVisibility(View.GONE);
            open = false;
        }
    }

    public void setDataModel(ChallengeCardDataModel model) {
        challengeText.setText(model.getNumDigits() + " digits");
    }

    @Override
    public void play(IChallengeSettingsModel model) {

    }

    @Override
    public void expand() {

    }

    @Override
    public void contract() {

    }
}