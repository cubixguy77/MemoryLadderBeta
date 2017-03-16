package selectChallenge.viewChallengeList;

import java.util.List;

import selectChallenge.viewChallengeCard.Challenge;

interface IChallengeListView {
    void renderChallengeList(List<Challenge> challenges);
}
