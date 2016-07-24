package speednumbers.mastersofmemory.challenges.presentation.views;

import java.util.List;

import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

public interface IChallengeListView {
    public void renderChallengeList(List<Challenge> challenges);
}
