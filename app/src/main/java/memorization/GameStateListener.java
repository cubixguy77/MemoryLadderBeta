package memorization;

import android.os.Bundle;

import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

public interface GameStateListener {

    void onLoad(Challenge challenge, Bundle savedInstanceState);
    void onMemorizationStart();
    void onTimeExpired();
    void onTransitionToRecall();
    void onRecallComplete(Result result);
    void onPlayAgain();

}
