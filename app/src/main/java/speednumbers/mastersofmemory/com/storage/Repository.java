package speednumbers.mastersofmemory.com.storage;

import java.util.List;

import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.domain.model.Game;
import speednumbers.mastersofmemory.com.domain.model.Setting;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;

public class Repository implements IRepository.Challenge, IRepository.Game, IRepository.ChallengeSettings {
    @Override
    public List<Challenge> getChallengeList(int gameKey, IRepository.GetChallengesCallback callback) {
        return null;
    }

    @Override
    public boolean deleteChallenge(IRepository.Challenge challenge) {
        return false;
    }

    @Override
    public boolean insertChallenge(IRepository.Challenge challenge) {
        return false;
    }

    @Override
    public List<Setting> getSettingsList(int challengeKey, IRepository.GetSettingsCallback callback) {
        return null;
    }

    @Override
    public boolean updateSetting(Setting setting) {
        return false;
    }

    @Override
    public List<Game> getGameList(IRepository.GetGamesCallback callback) {
        return null;
    }
}
