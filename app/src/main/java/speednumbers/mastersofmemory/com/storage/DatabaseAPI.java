package speednumbers.mastersofmemory.com.storage;

import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.domain.model.Game;
import speednumbers.mastersofmemory.com.domain.model.Setting;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;

public interface DatabaseAPI {
        void getGameList(IRepository.GetGamesCallback callback);

        void getChallengeList(int gameKey, IRepository.GetChallengesCallback callback);
        boolean deleteChallenge(Challenge challenge);
        long insertChallenge(Challenge challenge);

        void getSettingsList(int challengeKey, IRepository.GetSettingsCallback callback);
        boolean updateSetting(Setting setting);

}
