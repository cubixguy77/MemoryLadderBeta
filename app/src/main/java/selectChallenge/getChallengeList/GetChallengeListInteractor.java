package selectChallenge.getChallengeList;

import java.util.List;

import selectChallenge.viewChallengeCard.Challenge;
import framework.interactors.Interactor;


public interface GetChallengeListInteractor extends Interactor {

    void setCallback(GetChallengeListInteractor.Callback callback);

    interface Callback {
        void onChallengeListLoaded(List<Challenge> challenges);
    }
}
