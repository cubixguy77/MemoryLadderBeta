package storage;

import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.domain.model.Setting;
import repository.IRepository;

public interface DatabaseAPI {
        void getGameList(IRepository.GetGamesCallback callback);

        void getChallengeList(long gameKey, IRepository.GetChallengesCallback callback);
        boolean deleteChallenge(Challenge challenge);
        long insertChallenge(Challenge challenge, IRepository.AddedChallengeCallback addedChallengeCallback);

        void getSettingsList(long challengeKey, IRepository.GetSettingsCallback callback);
        boolean updateSetting(Setting setting);

}
