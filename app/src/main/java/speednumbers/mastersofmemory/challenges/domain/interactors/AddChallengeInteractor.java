package speednumbers.mastersofmemory.challenges.domain.interactors;

import interactors.base.Interactor;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

public interface AddChallengeInteractor extends Interactor {
    void setCallback(AddChallengeInteractor.Callback callback);

    interface Callback {
        void onChallengeAdded(Challenge challenge);
    }
}
