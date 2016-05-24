package speednumbers.mastersofmemory.com.storage;

import java.util.List;

import javax.inject.Inject;

import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.domain.model.Game;
import speednumbers.mastersofmemory.com.domain.model.Setting;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;

public class Repository implements IRepository {


    private final DatabaseAPI db;

    @Inject
    public Repository(DatabaseAPI db) {
        this.db = db;
    }

    @Override
    public void getGameList(final IRepository.GetGamesCallback callback) {
        db.getGameList(new IRepository.GetGamesCallback() {
            @Override
            public void onGamesLoaded(List<Game> games) {
                callback.onGamesLoaded(games);
            }
        });
    }




    @Override
    public void getChallengeList(long gameKey, final IRepository.GetChallengesCallback callback) {
        db.getChallengeList(gameKey, new IRepository.GetChallengesCallback() {
            @Override
            public void onChallengesLoaded(List<Challenge> challenges) {
                callback.onChallengesLoaded(challenges);
            }
        });
    }

    @Override
    public boolean deleteChallenge(Challenge challenge) {
        db.deleteChallenge(challenge);
        return true;
    }

    @Override
    public boolean insertChallenge(Challenge challenge) {
        db.insertChallenge(challenge);
        return true;
    }




    @Override
    public void getSettingsList(long challengeKey, final IRepository.GetSettingsCallback callback) {
        db.getSettingsList(challengeKey, new IRepository.GetSettingsCallback() {
            @Override
            public void onSettingsLoaded(List<Setting> settings) {
                callback.onSettingsLoaded(settings);
            }
        });
    }

    @Override
    public boolean updateSetting(Setting setting) {
        db.updateSetting(setting);
        return true;
    }
}
