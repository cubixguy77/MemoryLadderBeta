package speednumbers.mastersofmemory.com.domain.interactors;

import speednumbers.mastersofmemory.com.domain.interactors.base.Interactor;
import speednumbers.mastersofmemory.com.domain.model.Challenge;

public interface DeleteChallengeInteractor extends Interactor {
    void deleteChallenge(Challenge challenge);
}
