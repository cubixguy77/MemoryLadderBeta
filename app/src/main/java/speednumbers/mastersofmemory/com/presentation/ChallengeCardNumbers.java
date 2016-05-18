package speednumbers.mastersofmemory.com.presentation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;

public class ChallengeCardNumbers extends ChallengeCard implements IChallengeCardNumbers.View {

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
    }

    @Override
    public void selectDigitsPerGroup(int digitsPerGroup) {

    }

    @Override
    public void deselectDigitsPerGroup(int digitsPerGroup) {

    }
}
