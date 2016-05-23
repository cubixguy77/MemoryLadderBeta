package speednumbers.mastersofmemory.com.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.presentation.injection.components.ChallengeComponent;

public class ChallengeListFragment extends BaseFragment implements  IChallengeListView {

    @Inject ChallengeListPresenter challengeListPresenter;

    public ChallengeListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(ChallengeComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_challenge_list, container, true);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.challengeListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadChallengeList();
        }
    }

    private void loadChallengeList() {
        this.challengeListPresenter.loadChallengeList();
    }

    @Override
    public void renderChallengeList(List<Challenge> challenges) {
        for (Challenge challenge : challenges) {
            System.out.println("Challenge Received: " + challenge.getTitle());
        }
    }
}