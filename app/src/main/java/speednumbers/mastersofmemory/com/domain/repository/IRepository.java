package speednumbers.mastersofmemory.com.domain.repository;

import java.util.List;

import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.domain.model.Game;
import speednumbers.mastersofmemory.com.domain.model.Setting;

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

    void getGameList(GetGamesCallback callback);


    void getChallengeList(long gameKey, GetChallengesCallback callback);
    boolean deleteChallenge(Challenge challenge);
    boolean insertChallenge(Challenge challenge, AddedChallengeCallback callback);


    void getSettingsList(long challengeKey, GetSettingsCallback callback);
    boolean updateSetting(Setting setting);

}
