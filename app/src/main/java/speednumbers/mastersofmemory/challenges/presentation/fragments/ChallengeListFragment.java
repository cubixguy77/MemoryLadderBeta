package speednumbers.mastersofmemory.challenges.presentation.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import injection.components.ChallengeListComponent;
import speednumbers.mastersofmemory.challenges.domain.interactors.AddChallengeInteractor;
import speednumbers.mastersofmemory.challenges.domain.interactors.DeleteChallengeInteractor;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.presentation.views.ChallengeCardNumbers;
import speednumbers.mastersofmemory.challenges.presentation.presenters.ChallengeListPresenter;
import speednumbers.mastersofmemory.challenges.presentation.IAddChallengeListener;
import speednumbers.mastersofmemory.challenges.presentation.views.IChallengeListView;
import speednumbers.mastersofmemory.challenges.presentation.IDeleteChallengeListener;
import speednumbers.mastersofmemory.com.presentation.R;

public class ChallengeListFragment extends BaseFragment implements IChallengeListView, IDeleteChallengeListener, IAddChallengeListener {

    @Inject
    ChallengeListPresenter challengeListPresenter;
    @Inject
    DeleteChallengeInteractor deleteChallengeInteractor;
    @Inject
    AddChallengeInteractor addChallengeInteractor;
    @BindView(R.id.ChallengeListContainer) LinearLayout challengeListContainer;

    public ChallengeListFragment() {
        setRetainInstance(false);
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
        this.getComponent(ChallengeListComponent.class).inject(this);
        this.challengeListPresenter.setView(this);

        /* TODO: Store the challenges on rotation so they don't have to be looked up a second time */
        this.loadChallengeList();
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
    public void onDeleteChallenge(Challenge challenge, ChallengeCardNumbers card) {
        System.out.println("~~~~~~Deleting the following challenge~~~~~~~");
        System.out.println(challenge.toString());
        removeChallenge(challenge, card);
    }

    @Override
    public void onChallengeAdd() {
        System.out.println("Yes, challenge addition time");

        addChallengeInteractor.setCallback(new AddChallengeInteractor.Callback() {
            @Override
            public void onChallengeAdded(Challenge challenge) {
                List<Challenge> challengeList = Collections.singletonList(challenge);
                renderChallengeList(challengeList);
            }
        });

        addChallengeInteractor.execute();
    }

    private void removeChallenge(final Challenge challenge, final ChallengeCardNumbers card) {
        card.setVisibility(View.GONE);

        Snackbar snackbar = Snackbar
            .make(challengeListContainer, "Challenge deleted", Snackbar.LENGTH_LONG)
            .setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    card.setVisibility(View.VISIBLE);
                    Snackbar.make(challengeListContainer, "Challenge is restored!", Snackbar.LENGTH_SHORT).show();
                }
            });

        snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if (event != DISMISS_EVENT_ACTION) {
                    deleteChallengeInteractor.deleteChallenge(challenge);
                    System.out.println("Challenge truly deleted");
                }
            }
        });

        snackbar.show();
    }


    private void loadChallengeList() {
        System.out.println("loadChallengeList()");
        this.challengeListPresenter.loadChallengeList();
    }

    @Override
    public void renderChallengeList(final List<Challenge> challenges) {
        System.out.println("View: Challenges received");
        for (final Challenge challenge : challenges) {
            //System.out.println(challenge.toString());
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    ChallengeCardNumbers card = new ChallengeCardNumbers(getActivity(), challenge, ChallengeListFragment.this);
                    challengeListContainer.addView(card, challenges.size() > 1 ? -1 : 0);
                    //System.out.println("Challenge added!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
            });
        }
    }
}