package selectChallenge.addChallenge;

import framework.interactors.Interactor;
import selectChallenge.viewChallengeCard.Challenge;

public interface AddChallengeInteractor extends Interactor {
    void setCallback(AddChallengeInteractor.Callback callback);
    void setModel(int numDigits);

    interface Callback {
        void onChallengeAdded(Challenge challenge);
    }
}
