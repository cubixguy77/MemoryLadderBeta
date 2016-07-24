package speednumbers.mastersofmemory.challenges.domain.interactors;

import java.util.List;

import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import interactors.base.Interactor;


public interface GetChallengeListInteractor extends Interactor {

    void setCallback(GetChallengeListInteractor.Callback callback);

    interface Callback {
        void onChallengeListLoaded(List<Challenge> challenges);
    }
}
