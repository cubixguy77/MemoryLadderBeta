package database.repository;

import java.util.List;

import playChallenge.writtenNumbersChallenge.review.Result;
import scores.viewScoreList.Score;
import selectChallenge.viewChallengeCard.Challenge;
import database.Game;
import speednumbers.mastersofmemory.challengelist.challenge.settings.Setting;

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

    interface GetScoresCallback {
        void onScoresLoaded(List<Score> scores);
    }

    void getGameList(GetGamesCallback callback);

    void getChallenge(long challengeKey, GetChallengeCallback callback);
    void getChallengeList(long gameKey, GetChallengesCallback callback);
    boolean deleteChallenge(Challenge challenge);
    boolean insertChallenge(Challenge challenge, AddedChallengeCallback callback);


    void getSettingsList(long challengeKey, GetSettingsCallback callback);
    boolean updateSetting(Setting setting);

    void insertScore(Result result, IRepository.InsertScoreCallback callback);
    void getScoreList(long challengeKey, IRepository.GetScoresCallback callback);
}
