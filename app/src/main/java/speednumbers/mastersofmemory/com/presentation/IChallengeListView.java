package speednumbers.mastersofmemory.com.presentation;

import java.util.List;

import speednumbers.mastersofmemory.com.domain.model.Challenge;

public interface IChallengeListView {
    public void renderChallengeList(List<Challenge> challenges);
}
