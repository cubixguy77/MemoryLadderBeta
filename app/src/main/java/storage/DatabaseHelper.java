package storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import review.Result;
import scores.Score;
import speednumbers.mastersofmemory.challenges.MyApplication;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.domain.model.Game;
import speednumbers.mastersofmemory.challenges.domain.model.Setting;
import repository.IRepository;

public class DatabaseHelper extends SQLiteOpenHelper implements DatabaseAPI {


    // Database Info
    private static final String DATABASE_NAME = "MemoryDatabase";
    private static final int DATABASE_VERSION = 2;

    @Inject
    DatabaseHelper() {
        super(MyApplication.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Database: onCreate()");

        db.execSQL(GameTableContract.GameTable.CREATE_GAME_TABLE);
        db.execSQL(ChallengeTableContract.ChallengeTable.CREATE_CHALLENGE_TABLE);
        db.execSQL(ChallengeSettingTableContract.ChallengeSettingTable.CREATE_CHALLENGE_SETTING_TABLE);
        db.execSQL(SettingTableContract.SettingTable.CREATE_SETTING_TABLE);
        db.execSQL(ScoreTableContract.ScoreTable.CREATE_SCORE_TABLE);

        long gameKey = insertGame(db, new Game("Speed Numbers", "speedNumbersIcon.png"));

        ArrayList<Setting> settings = new ArrayList<>();
        Setting numDigits = new Setting(-1, -1, 10, "Number of Digits", 10, true);
        Setting digitsPerGroup = new Setting(-1, -1, 1, "Digits Per Group", 20, true);
        Setting memTimer = new Setting(-1, -1, 30, "Memorization Timer", 30, true);
        Setting recallTimer = new Setting(-1, -1, 60, "Recall Timer", 40, true);

        // Populate Setting table
        insertSetting(db, numDigits);
        settings.add(numDigits);
        insertSetting(db, digitsPerGroup);
        settings.add(digitsPerGroup);
        insertSetting(db, memTimer);
        settings.add(memTimer);
        insertSetting(db, recallTimer);
        settings.add(recallTimer);

        insertChallenge(db, new Challenge(gameKey, -1, "10 Digits", false, settings));

        settings.get(0).setValue(20);  // Digits Per Group
        settings.get(1).setValue(2);  // Digits Per Group
        settings.get(2).setValue(45); // Mem Timer
        settings.get(3).setValue(90); // Recall Timer

        insertChallenge(db, new Challenge(gameKey, -1, "20 Digits", false, settings));

        settings.get(0).setValue(30);  // Digits Per Group
        settings.get(1).setValue(3);   // Digits Per Group
        settings.get(2).setValue(60);  // Mem Timer
        settings.get(3).setValue(120); // Recall Timer

        insertChallenge(db, new Challenge(gameKey, -1, "30 Digits", false, settings));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("Database: onUpgrade(): Old" + oldVersion + " new" + newVersion);

        switch (newVersion) {

        /* Version 2
         * Introduces saved scores feature
         * We need to create a table to save those scores
         */
            case 2:
                db.execSQL(ScoreTableContract.ScoreTable.CREATE_SCORE_TABLE);
        }
    }

    @Override
    public void getGameList(IRepository.GetGamesCallback callback) {
        SQLiteDatabase db = getReadableDatabase();
        List<Game> games = new ArrayList<>();

        String SELECT_QUERY = String.format
        (
            "SELECT " +
                GameTableContract.GameTable.GAME_GAME_KEY + "," +
                GameTableContract.GameTable.GAME_TITLE + "," +
                GameTableContract.GameTable.GAME_ICON_PATH +  " " +
            "FROM %s",
            GameTableContract.GameTable.TABLE_NAME
        );

        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Game game = new Game
                    (
                        cursor.getLong(cursor.getColumnIndex(GameTableContract.GameTable.GAME_GAME_KEY)),
                        cursor.getString(cursor.getColumnIndex(GameTableContract.GameTable.GAME_TITLE)),
                        cursor.getString(cursor.getColumnIndex(GameTableContract.GameTable.GAME_ICON_PATH))
                    );

                    games.add(game);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERROR", "Error while trying to get games from database!");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        callback.onGamesLoaded(games);
    }

    private long insertGame(SQLiteDatabase db, Game game) {
        db.beginTransaction();
        long gameKey = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(GameTableContract.GameTable.GAME_TITLE, game.getTitle());
            values.put(GameTableContract.GameTable.GAME_ICON_PATH, game.getIconPath());
            gameKey = db.insert(GameTableContract.GameTable.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            Log.d("ERROR", "Error while trying to add game to database");
        } finally {
            db.endTransaction();
        }

        return gameKey;
    }


    @Override
    public void getChallenge(long challengeKey, final IRepository.GetChallengeCallback callback) {
        SQLiteDatabase db = getReadableDatabase();
        Challenge challenge = null;

        String SELECT_QUERY = String.format
                (
                        "SELECT " +
                                ChallengeTableContract.ChallengeTable.CHALLENGE_GAME_KEY + "," +
                                ChallengeTableContract.ChallengeTable.CHALLENGE_CHALLENGE_KEY + "," +
                                ChallengeTableContract.ChallengeTable.CHALLENGE_TITLE + "," +
                                ChallengeTableContract.ChallengeTable.CHALLENGE_LOCKED + " " +
                                "FROM %s " +
                                "WHERE %s.%s = %s",
                        ChallengeTableContract.ChallengeTable.TABLE_NAME,
                        ChallengeTableContract.ChallengeTable.TABLE_NAME, ChallengeTableContract.ChallengeTable.CHALLENGE_CHALLENGE_KEY, challengeKey
                );

        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    challenge = new Challenge
                        (
                            cursor.getLong(cursor.getColumnIndex(ChallengeTableContract.ChallengeTable.CHALLENGE_GAME_KEY)),
                            cursor.getLong(cursor.getColumnIndex(ChallengeTableContract.ChallengeTable.CHALLENGE_CHALLENGE_KEY)),
                            cursor.getString(cursor.getColumnIndex(ChallengeTableContract.ChallengeTable.CHALLENGE_TITLE)),
                            cursor.getInt(cursor.getColumnIndex(ChallengeTableContract.ChallengeTable.CHALLENGE_LOCKED)) == 1,
                            null
                        );
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERROR", "Error while trying to get challenges from database!");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        if (challenge == null)
            return;

        final Challenge finalChallenge = challenge;
        getSettingsList(challengeKey, new IRepository.GetSettingsCallback() {
            @Override
            public void onSettingsLoaded(List<Setting> settings) {
                finalChallenge.setSettings(settings);
                callback.onChallengeLoaded(finalChallenge);
            }
        });
    }


    @Override
    public void getChallengeList(long gameKey, IRepository.GetChallengesCallback callback) {
        SQLiteDatabase db = getReadableDatabase();
        List<Challenge> challenges = new ArrayList<>();

        String SELECT_QUERY = String.format
        (
            "SELECT " +
                ChallengeTableContract.ChallengeTable.CHALLENGE_GAME_KEY + "," +
                ChallengeTableContract.ChallengeTable.CHALLENGE_CHALLENGE_KEY + "," +
                ChallengeTableContract.ChallengeTable.CHALLENGE_TITLE + "," +
                ChallengeTableContract.ChallengeTable.CHALLENGE_LOCKED + " " +
            "FROM %s " +
            "WHERE %s.%s = %s",
            ChallengeTableContract.ChallengeTable.TABLE_NAME,
            ChallengeTableContract.ChallengeTable.TABLE_NAME, ChallengeTableContract.ChallengeTable.CHALLENGE_GAME_KEY, gameKey
        );

        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Challenge challenge = new Challenge
                    (
                        cursor.getLong(cursor.getColumnIndex(ChallengeTableContract.ChallengeTable.CHALLENGE_GAME_KEY)),
                        cursor.getLong(cursor.getColumnIndex(ChallengeTableContract.ChallengeTable.CHALLENGE_CHALLENGE_KEY)),
                        cursor.getString(cursor.getColumnIndex(ChallengeTableContract.ChallengeTable.CHALLENGE_TITLE)),
                        cursor.getInt(cursor.getColumnIndex(ChallengeTableContract.ChallengeTable.CHALLENGE_LOCKED)) == 1,
                        null
                    );

                    challenges.add(challenge);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERROR", "Error while trying to get challenges from database!");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        callback.onChallengesLoaded(challenges);
    }

    @Override
    public boolean deleteChallenge(Challenge challenge) {
        deleteChallengeSettings(challenge);
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(ChallengeTableContract.ChallengeTable.TABLE_NAME, ChallengeTableContract.ChallengeTable.CHALLENGE_CHALLENGE_KEY + "=" + challenge.getChallengeKey(), null) > 0;
    }

    private boolean deleteChallengeSettings(Challenge challenge) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ChallengeSettingTableContract.ChallengeSettingTable.TABLE_NAME, ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_CHALLENGE_KEY + "=" + challenge.getChallengeKey(), null);
        return true;
    }

    @Override
    public long insertChallenge(Challenge challenge, IRepository.AddedChallengeCallback addedChallengeCallback) {
        Challenge addedChallenge = insertChallenge(getWritableDatabase(), challenge);
        if (addedChallengeCallback != null) {
            addedChallengeCallback.onChallengeAdded(addedChallenge);
        }

        return addedChallenge.getChallengeKey();
    }

    private Challenge insertChallenge(SQLiteDatabase db, Challenge challenge) {
        db.beginTransaction();

        long challengeKey = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(ChallengeTableContract.ChallengeTable.CHALLENGE_GAME_KEY, challenge.getGameKey());
            values.put(ChallengeTableContract.ChallengeTable.CHALLENGE_TITLE, challenge.getTitle());
            values.put(ChallengeTableContract.ChallengeTable.CHALLENGE_LOCKED, challenge.isLocked() ? 1 : 0);
            challengeKey = db.insert(ChallengeTableContract.ChallengeTable.TABLE_NAME, null, values);
            challenge.setChallengeKey(challengeKey);

            if (challenge.getSettings() != null) {
                for (Setting setting : challenge.getSettings()) {
                    setting.setChallengeKey(challengeKey);
                    insertChallengeSetting(db, challengeKey, setting.getSettingKey(), setting.getValue());
                }
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            Log.d("ERROR", "Error while trying to add challenge to database");
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }


        return challenge;
    }

    @Override
    public void insertScore(Result result, IRepository.InsertScoreCallback callback) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(ScoreTableContract.ScoreTable.CHALLENGE_KEY, result.getChallengeKey());
            values.put(ScoreTableContract.ScoreTable.SCORE, result.getNumDigitsRecalledCorrectly());
            values.put(ScoreTableContract.ScoreTable.MEM_TIME, result.getMemTime());
            values.put(ScoreTableContract.ScoreTable.DATE_TIME, System.currentTimeMillis());

            db.insert(ScoreTableContract.ScoreTable.TABLE_NAME, null, values);
            db.setTransactionSuccessful();

            callback.onScoreAdded(result);
        }
        catch (Exception e) {
            Log.d("ERROR", "Error while trying to add challenge to database");
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }


    @Override
    public void getScoreList(long challengeKey, IRepository.GetScoresCallback callback) {
        SQLiteDatabase db = getReadableDatabase();
        List<Score> scores = new ArrayList<>();

        String SELECT_QUERY = String.format
        (
            "SELECT " +
                "S1." + ScoreTableContract.ScoreTable.SCORE + "," +
                "S1." + ScoreTableContract.ScoreTable.MEM_TIME + "," +
                "S1." + ScoreTableContract.ScoreTable.DATE_TIME + " " +
            "FROM %s AS S1 " +
            "WHERE %s " +
            "ORDER BY %s",
            ScoreTableContract.ScoreTable.TABLE_NAME, // FROM
            "S1." + ScoreTableContract.ScoreTable.CHALLENGE_KEY + " = " + challengeKey, // WHERE
            "S1." + ScoreTableContract.ScoreTable.SCORE + " DESC, S1." + ScoreTableContract.ScoreTable.MEM_TIME + " ASC" + ", S1." + ScoreTableContract.ScoreTable.DATE_TIME + " DESC" // ORDER BY
        );

        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Score score = new Score
                    (
                        cursor.getPosition()+1, // The results are already sorted via SQL, the row index is the score's "Rank". Add one to make ranks start at one.
                        cursor.getInt(cursor.getColumnIndex(ScoreTableContract.ScoreTable.SCORE)),
                        cursor.getInt(cursor.getColumnIndex(ScoreTableContract.ScoreTable.MEM_TIME)),
                        new Date(cursor.getLong(cursor.getColumnIndex(ScoreTableContract.ScoreTable.DATE_TIME)))
                    );

                    scores.add(score);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.println("Error: Error looking up scores.");
            Log.d("ERROR", "Error while trying to get scores from database!");
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        callback.onScoresLoaded(scores);
    }


    @Override
    public void getSettingsList(long challengeKey, IRepository.GetSettingsCallback callback) {
        SQLiteDatabase db = getReadableDatabase();
        List<Setting> settings = new ArrayList<>();

        String SELECT_QUERY = String.format
        (
            "SELECT " +
                ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_CHALLENGE_KEY + "," +
                ChallengeSettingTableContract.ChallengeSettingTable.TABLE_NAME + "." + ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_SETTING_KEY + "," +
                ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_VALUE + "," +
                SettingTableContract.SettingTable.SETTING_NAME + "," +
                SettingTableContract.SettingTable.SETTING_SORT_ORDER + "," +
                SettingTableContract.SettingTable.SETTING_VISIBLE + " " +
            "FROM %s " +
            "JOIN %s " +
            "  ON %s.%s = %s.%s " +
            "WHERE %s.%s = %s " +
            "ORDER BY %s",
            ChallengeSettingTableContract.ChallengeSettingTable.TABLE_NAME,
            SettingTableContract.SettingTable.TABLE_NAME,
            ChallengeSettingTableContract.ChallengeSettingTable.TABLE_NAME, ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_SETTING_KEY,
            SettingTableContract.SettingTable.TABLE_NAME, SettingTableContract.SettingTable.SETTING_SETTING_KEY,
            ChallengeSettingTableContract.ChallengeSettingTable.TABLE_NAME, ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_CHALLENGE_KEY, challengeKey,
            SettingTableContract.SettingTable.SETTING_SORT_ORDER + ", " + SettingTableContract.SettingTable.SETTING_VISIBLE
        );

        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Setting setting = new Setting
                    (
                        cursor.getLong(cursor.getColumnIndex(ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_CHALLENGE_KEY)),
                        cursor.getLong(cursor.getColumnIndex(ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_SETTING_KEY)),
                        cursor.getInt(cursor.getColumnIndex(ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_VALUE)),
                        cursor.getString(cursor.getColumnIndex(SettingTableContract.SettingTable.SETTING_NAME)),
                        cursor.getInt(cursor.getColumnIndex(SettingTableContract.SettingTable.SETTING_SORT_ORDER)),
                        cursor.getInt(cursor.getColumnIndex(SettingTableContract.SettingTable.SETTING_VISIBLE)) == 1
                    );
                    settings.add(setting);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERROR", "Error while trying to get settings from database!");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        callback.onSettingsLoaded(settings);
    }

    @Override
    public boolean updateSetting(Setting setting) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_VALUE, setting.getValue());
        db.update(ChallengeSettingTableContract.ChallengeSettingTable.TABLE_NAME, values,
                ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_CHALLENGE_KEY + "=" + setting.getChallengeKey()
                + " AND " + ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_SETTING_KEY + "=" + setting.getSettingKey(), null);

        return true;
    }

    /*
    private boolean recordExists(String TableName, String dbfield, String fieldValue) {
        SQLiteDatabase db = getReadableDatabase();
        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    */

    private long insertSetting(SQLiteDatabase db, Setting setting) {
        if (setting.getSettingKey() <= 0) {
            ContentValues values = new ContentValues();
            values.put(SettingTableContract.SettingTable.SETTING_NAME, setting.getSettingName());
            values.put(SettingTableContract.SettingTable.SETTING_SORT_ORDER, setting.getSortOrder());
            values.put(SettingTableContract.SettingTable.SETTING_VISIBLE, setting.isVisible() ? 1 : 0);
            long settingKey = db.insert(SettingTableContract.SettingTable.TABLE_NAME, null, values);
            setting.setSettingKey(settingKey);
        }

        return setting.getSettingKey();
    }

    private boolean insertChallengeSetting(SQLiteDatabase db, long challengeKey, long settingKey, int value) {
        ContentValues values = new ContentValues();
        values.put(ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_CHALLENGE_KEY, challengeKey);
        values.put(ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_SETTING_KEY, settingKey);
        values.put(ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_VALUE, value);
        db.insert(ChallengeSettingTableContract.ChallengeSettingTable.TABLE_NAME, null, values);
        return true;
    }


    private boolean deleteSetting(Setting setting) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ChallengeSettingTableContract.ChallengeSettingTable.TABLE_NAME, ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_SETTING_KEY + "=" + setting.getSettingKey(), null);
        db.delete(SettingTableContract.SettingTable.TABLE_NAME, SettingTableContract.SettingTable.SETTING_SETTING_KEY + "=" + setting.getSettingKey(), null);
        return true;
    }
}
