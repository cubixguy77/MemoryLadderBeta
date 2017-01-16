package repository;

import java.util.List;

import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.domain.model.Game;
import speednumbers.mastersofmemory.challenges.domain.model.Setting;

public interface IRepository {

    interface GetGamesCallback {
        void onGamesLoaded(List<Game> games);
    }

    interface GetChallengesCallback {
        void onChallengesLoaded(List<Challenge> challenges);
    }

    interface GetSettingsCallback {
        void onSettingsLoaded(List<Setting> settings);
    }

    interface AddedChallengeCallback {
        void onChallengeAdded(Challenge challenge);
    }

    interface GetChallengeCallback {
        void onChallengeLoaded(Challenge challenge);
    }

    interface InsertScoreCallback {
        void onScoreAdded(Result result);
    }

    void getGameList(GetGamesCallback callback);

    void getChallenge(long challengeKey, GetChallengeCallback callback);
    void getChallengeList(long gameKey, GetChallengesCallback callback);
    boolean deleteChallenge(Challenge challenge);
    boolean insertChallenge(Challenge challenge, AddedChallengeCallback callback);


    void getSettingsList(long challengeKey, GetSettingsCallback callback);
    boolean updateSetting(Setting setting);

    void insertScore(Result result, IRepository.InsertScoreCallback callback);
}
