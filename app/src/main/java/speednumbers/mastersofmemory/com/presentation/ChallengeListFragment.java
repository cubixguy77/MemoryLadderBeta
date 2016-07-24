package speednumbers.mastersofmemory.com.presentation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import speednumbers.mastersofmemory.com.domain.interactors.DeleteChallengeInteractor;
import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.presentation.injection.components.ChallengeComponent;

public class ChallengeListFragment extends BaseFragment implements  IChallengeListView, IDeleteChallengeListener, IAddChallengeListener  {

    @Inject ChallengeListPresenter challengeListPresenter;
    @Inject public DeleteChallengeInteractor deleteChallengeInteractor;
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
        //super.onCreateView(inflater, container, savedInstanceState);
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

    @Override
    public void onDeleteChallenge(Challenge challenge) {
        System.out.println("~~~~~~Deleting the following challenge~~~~~~~");
        System.out.println(challenge.toString());
        removeChallenge(challenge);
    }

    @Override
    public void onChallengeAdded(Challenge challenge) {
        System.out.println("Yes, challenge received");
    }

    public void removeChallenge(final Challenge challenge) {
        final int viewIndex = 0;
        final View challengeCardToDelete = challengeListContainer.getChildAt(viewIndex);
        challengeCardToDelete.setVisibility(View.GONE);

        Snackbar snackbar = Snackbar
            .make(challengeListContainer, "Challenge deleted", Snackbar.LENGTH_LONG)
            .setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    challengeCardToDelete.setVisibility(View.VISIBLE);
                    Snackbar.make(challengeListContainer, "Challenge is restored!", Snackbar.LENGTH_SHORT).show();
                }
            });

        snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if (event == DISMISS_EVENT_TIMEOUT) {
                    deleteChallengeInteractor.deleteChallenge(challenge);
                    challengeListContainer.removeView(challengeCardToDelete);
                }
            }
        });

        snackbar.show();
    }


    private void loadChallengeList() {
        this.challengeListPresenter.loadChallengeList();
    }

    @Override
    public void renderChallengeList(List<Challenge> challenges) {
        //Looper.prepare();

        System.out.println("View: Challenges received");
        for (final Challenge challenge : challenges) {
            System.out.println(challenge.toString());
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    ChallengeCardNumbers card = new ChallengeCardNumbers(getActivity(), challenge, ChallengeListFragment.this);
                    challengeListContainer.addView(card);
                    System.out.println("Challenge added!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
            });

        }
    }
}