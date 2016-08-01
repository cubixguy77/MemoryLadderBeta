package speednumbers.mastersofmemory.challenges.domain.interactors;

import interactors.base.Interactor;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

public interface GetChallengeInteractor extends Interactor {

    void setCallback(GetChallengeInteractor.Callback callback);

    interface Callback {
        void onChallengeLoaded(Challenge challenge);
    }
}
