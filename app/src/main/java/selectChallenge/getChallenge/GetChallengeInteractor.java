package selectChallenge.getChallenge;

import framework.interactors.Interactor;
import selectChallenge.viewChallengeCard.Challenge;

public interface GetChallengeInteractor extends Interactor {

    void setCallback(GetChallengeInteractor.Callback callback);

    interface Callback {
        void onChallengeLoaded(Challenge challenge);
    }
}
