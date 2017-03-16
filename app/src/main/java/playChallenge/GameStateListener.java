package playChallenge;

import android.os.Bundle;

import playChallenge.writtenNumbersChallenge.review.Result;
import selectChallenge.viewChallengeCard.Challenge;

public interface GameStateListener {

    void onLoad(Challenge challenge, Bundle savedInstanceState);
    void onMemorizationStart();
    void onTimeExpired();
    void onTransitionToRecall();
    void onRecallComplete(Result result);
    void onPlayAgain();
    void onShutdown();

}
