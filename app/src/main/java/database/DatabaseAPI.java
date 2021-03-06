package database;

import playChallenge.writtenNumbersChallenge.review.Result;
import selectChallenge.viewChallengeCard.Challenge;
import speednumbers.mastersofmemory.challengelist.challenge.settings.Setting;
import database.repository.IRepository;

public interface DatabaseAPI {
        void getGameList(IRepository.GetGamesCallback callback);

        void getChallenge(long challengeKey, IRepository.GetChallengeCallback callback);
        void getChallengeList(long gameKey, IRepository.GetChallengesCallback callback);
        boolean deleteChallenge(Challenge challenge);

        long insertChallenge(Challenge challenge, IRepository.AddedChallengeCallback addedChallengeCallback);
        void getSettingsList(long challengeKey, IRepository.GetSettingsCallback callback);

        boolean updateSetting(Setting setting);

        void insertScore(Result result, IRepository.InsertScoreCallback callback);
        void getScoreList(long challengeKey, IRepository.GetScoresCallback callback);
}
