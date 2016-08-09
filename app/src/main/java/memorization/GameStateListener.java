package memorization;

import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

public interface GameStateListener {

    void onLoad(Challenge challenge);
    void onMemorizationStart();
    void onTimeExpired();
    void onTransitionToRecall();
    void onRecallComplete(Result result);

}
