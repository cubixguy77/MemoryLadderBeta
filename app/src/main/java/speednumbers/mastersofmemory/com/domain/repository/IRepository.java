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



    interface GameCallbacks {
        void getGameList(GetGamesCallback callback);
    }

    interface ChallengeCallbacks {
        void getChallengeList(int gameKey, GetChallengesCallback callback);
        boolean deleteChallenge(Challenge challenge);
        boolean insertChallenge(Challenge challenge);
    }

    interface ChallengeSettingsCallbacks {
        void getSettingsList(int challengeKey, GetSettingsCallback callback);
        boolean updateSetting(Setting setting);
    }
}
