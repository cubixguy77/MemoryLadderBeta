package speednumbers.mastersofmemory.com.domain.interactors;

import speednumbers.mastersofmemory.com.domain.interactors.base.Interactor;
import speednumbers.mastersofmemory.com.domain.model.Challenge;

public interface AddChallengeInteractor extends Interactor {
    void setCallback(AddChallengeInteractor.Callback callback);

    interface Callback {
        void onChallengeAdded(Challenge challenge);
    }
}
