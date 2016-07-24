package speednumbers.mastersofmemory.challenges.domain.interactors;

import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import interactors.base.Interactor;

public interface DeleteChallengeInteractor extends Interactor {
    void deleteChallenge(Challenge challenge);
}
