package review;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import speednumbers.mastersofmemory.com.presentation.R;

public class FinalScoreCardFragment extends android.support.v4.app.Fragment {

    private Result result;
    @BindView(R.id.recallNumberCorrect) TextView recallNumberCorrect;
    @BindView(R.id.digitsAttempted) TextView digitsAttempted;
    @BindView(R.id.percentRecalled) TextView percentRecalled;
    @BindView(R.id.memTime) TextView memTime;
    @BindView(R.id.digitSpeed) TextView digitSpeed;

    public FinalScoreCardFragment() {
        setRetainInstance(true);
    }

    public void setModel(Result result) {
        this.result = result;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_final_score_card, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recallNumberCorrect.setText(Integer.toString(result.getNumDigitsRecalledCorrectly()));
        digitsAttempted.setText("/ " + result.getNumDigitsTotal() + " Digits");
        percentRecalled.setText(result.getAccuracy() + "%");
        memTime.setText(result.getMemTime() + " Sec");
        digitSpeed.setText(result.getDigitsPerMinute() + " Digits");
    }

    @Override public void onResume() {
        super.onResume();
    }

    @Override public void onPause() {
        super.onPause();
    }

    @Override public void onDetach() {
        super.onDetach();
    }

}