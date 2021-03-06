package database;

import java.util.List;

import javax.inject.Inject;

import playChallenge.writtenNumbersChallenge.review.Result;
import selectChallenge.viewChallengeCard.Challenge;
import speednumbers.mastersofmemory.challengelist.challenge.settings.Setting;
import database.repository.IRepository;

public class Repository implements IRepository {

    private final DatabaseAPI db;

    @Inject
    Repository(DatabaseAPI db) {
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
    public void getChallenge(long challengeKey, GetChallengeCallback callback) {
        db.getChallenge(challengeKey, callback);
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
    public boolean insertChallenge(Challenge challenge, final AddedChallengeCallback callback) {
        db.insertChallenge(challenge, new IRepository.AddedChallengeCallback() {
            @Override
            public void onChallengeAdded(Challenge challenge) {
                callback.onChallengeAdded(challenge);
            }
        });
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

    @Override
    public void insertScore(Result result, final IRepository.InsertScoreCallback callback) {
        db.insertScore(result, callback);
    }

    @Override
    public void getScoreList(long challengeKey, GetScoresCallback callback) {
        db.getScoreList(challengeKey, callback);
    }
}