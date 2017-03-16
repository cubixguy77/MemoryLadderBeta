package selectChallenge.deleteChallenge;

import selectChallenge.viewChallengeCard.Challenge;
import framework.interactors.Interactor;

public interface DeleteChallengeInteractor extends Interactor {
    void deleteChallenge(Challenge challenge);
}
