package speednumbers.mastersofmemory.challenges.presentation;

import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.presentation.views.ChallengeCardNumbers;

public interface IDeleteChallengeListener {
    public void onDeleteChallenge(Challenge challenge, ChallengeCardNumbers card);
}
