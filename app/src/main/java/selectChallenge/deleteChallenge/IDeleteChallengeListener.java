package selectChallenge.deleteChallenge;

import selectChallenge.viewChallengeCard.Challenge;
import selectChallenge.viewChallengeCard.ChallengeCardNumbers;

public interface IDeleteChallengeListener {
    void onDeleteChallenge(Challenge challenge, ChallengeCardNumbers card);
}
