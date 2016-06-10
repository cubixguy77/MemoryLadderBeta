package speednumbers.mastersofmemory.com.presentation;

import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.presentation.injection.components.ChallengeComponent;

public class ChallengeListFragment extends BaseFragment implements  IChallengeListView {

    @Inject ChallengeListPresenter challengeListPresenter;
    @BindView(R.id.ChallengeListContainer) LinearLayout challengeListContainer;

    public ChallengeListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_challenge_list, container, false);
        ButterKnife.bind(this, fragmentView);

        return fragmentView;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getComponent(ChallengeComponent.class).inject(this);

        this.challengeListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadChallengeList();
        }
    }

    @Override public void onResume() {
        super.onResume();
        this.challengeListPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.challengeListPresenter.pause();
    }

    @Override public void onDetach() {
        super.onDetach();
        this.challengeListPresenter = null;
    }




    private void loadChallengeList() {
        this.challengeListPresenter.loadChallengeList();
    }

    @Override
    public void renderChallengeList(List<Challenge> challenges) {
        Looper.prepare();
        System.out.println("View: Challenges received");
        for (Challenge challenge : challenges) {
            System.out.println(challenge.toString());
            ChallengeCardNumbers card = new ChallengeCardNumbers(getActivity(), challenge);
            challengeListContainer.addView(card);
            System.out.println("Challenge added!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
    }
}