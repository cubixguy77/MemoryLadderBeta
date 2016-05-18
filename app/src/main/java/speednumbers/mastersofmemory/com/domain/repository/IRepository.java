package speednumbers.mastersofmemory.com.domain.repository;

import java.util.List;

import speednumbers.mastersofmemory.com.domain.model.Setting;

public interface IRepository {

    interface GetGamesCallback {
        void onGamesLoaded(List<speednumbers.mastersofmemory.com.domain.model.Game> games);
    }

    interface GetChallengesCallback {
        void onChallengesLoaded(List<speednumbers.mastersofmemory.com.domain.model.Challenge> challenges);
    }

    interface GetSettingsCallback {
        void onSettingsLoaded(List<speednumbers.mastersofmemory.com.domain.model.Setting> settings);
    }

    interface Game {
        List<speednumbers.mastersofmemory.com.domain.model.Game> getGameList(GetGamesCallback callback);
    }

    interface Challenge {
        List<speednumbers.mastersofmemory.com.domain.model.Challenge> getChallengeList(int gameKey, GetChallengesCallback callback);
        boolean deleteChallenge(Challenge challenge);
        boolean insertChallenge(Challenge challenge);
    }

    interface ChallengeSettings {
        List<speednumbers.mastersofmemory.com.domain.model.Setting> getSettingsList(int challengeKey, GetSettingsCallback callback);
        boolean updateSetting(Setting setting);
    }
}
