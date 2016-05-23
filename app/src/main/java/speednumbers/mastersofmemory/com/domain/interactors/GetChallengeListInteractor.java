package speednumbers.mastersofmemory.com.domain.interactors;

import java.util.List;

import speednumbers.mastersofmemory.com.domain.interactors.base.Interactor;
import speednumbers.mastersofmemory.com.domain.model.Challenge;


public interface GetChallengeListInteractor extends Interactor {

    interface Callback {
        void onChallengeListLoaded(List<Challenge> challenges);
    }
}
