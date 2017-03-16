package selectChallenge.deleteChallenge;

import selectChallenge.viewChallengeCard.Challenge;
import interactors.base.Interactor;

public interface DeleteChallengeInteractor extends Interactor {
    void deleteChallenge(Challenge challenge);
}
