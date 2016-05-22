package speednumbers.mastersofmemory.com.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import speednumbers.mastersofmemory.com.MyApplication;
import speednumbers.mastersofmemory.com.domain.model.Challenge;
import speednumbers.mastersofmemory.com.domain.model.Game;
import speednumbers.mastersofmemory.com.domain.model.Setting;
import speednumbers.mastersofmemory.com.domain.repository.IRepository;

public class DatabaseHelper extends SQLiteOpenHelper implements DatabaseAPI {


    // Database Info
    private static final String DATABASE_NAME = "MemoryDatabase";
    private static final int DATABASE_VERSION = 1;

    @Inject
    public DatabaseHelper() {
        super(MyApplication.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GameTableContract.GameTable.CREATE_GAME_TABLE);
        db.execSQL(ChallengeTableContract.ChallengeTable.CREATE_CHALLENGE_TABLE);
        System.out.println(ChallengeTableContract.ChallengeTable.CREATE_CHALLENGE_TABLE);
        db.execSQL(ChallengeSettingTableContract.ChallengeSettingTable.CREATE_CHALLENGE_SETTING_TABLE);
        db.execSQL(SettingTableContract.SettingTable.CREATE_SETTING_TABLE);

        long gameKey = insertGame(db, new Game("Speed Numbers", "speedNumbersIcon.png"));
        insertChallenge(db, new Challenge(gameKey, -1, "11 Digits", false, null));
        insertChallenge(db, new Challenge(gameKey, -1, "22 Digits", false, null));
        insertChallenge(db, new Challenge(gameKey, -1, "33 Digits", false, null));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + GameTableContract.GameTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ChallengeTableContract.ChallengeTable.TABLE_NAME);
            onCreate(db);
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
    public void getChallengeList(int gameKey, IRepository.GetChallengesCallback callback) {
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
        for (Setting setting : challenge.getSettings()) {
            deleteSetting(setting);
        }

        SQLiteDatabase db = getWritableDatabase();
        return db.delete(ChallengeTableContract.ChallengeTable.TABLE_NAME, ChallengeTableContract.ChallengeTable.CHALLENGE_CHALLENGE_KEY + "=" + challenge.getChallengeKey(), null) > 0;
    }

    @Override
    public long insertChallenge(Challenge challenge) {
        return insertChallenge(getWritableDatabase(), challenge);
    }

    private long insertChallenge(SQLiteDatabase db, Challenge challenge) {
        db.beginTransaction();

        long challengeKey = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(ChallengeTableContract.ChallengeTable.CHALLENGE_GAME_KEY, challenge.getGameKey());
            values.put(ChallengeTableContract.ChallengeTable.CHALLENGE_TITLE, challenge.getTitle());
            values.put(ChallengeTableContract.ChallengeTable.CHALLENGE_LOCKED, challenge.isLocked() ? 1 : 0);
            challengeKey = db.insert(ChallengeTableContract.ChallengeTable.TABLE_NAME, null, values);

            if (challenge.getSettings() != null) {
                for (Setting setting : challenge.getSettings()) {
                    setting.setChallengeKey(challengeKey);
                    insertSetting(setting);
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

        return challengeKey;
    }





    @Override
    public void getSettingsList(int challengeKey, IRepository.GetSettingsCallback callback) {
        SQLiteDatabase db = getReadableDatabase();
        List<Setting> settings = new ArrayList<>();

        String SELECT_QUERY = String.format
        (
            "SELECT " +
                ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_CHALLENGE_KEY + "," +
                ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_SETTING_KEY + "," +
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

    private boolean insertSetting(Setting setting) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SettingTableContract.SettingTable.SETTING_NAME, setting.getSettingName());
        values.put(SettingTableContract.SettingTable.SETTING_SORT_ORDER, setting.getSortOrder());
        values.put(SettingTableContract.SettingTable.SETTING_VISIBLE, setting.isVisible() ? 1 : 0);
        long settingKey = db.insert(ChallengeTableContract.ChallengeTable.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_CHALLENGE_KEY, setting.getChallengeKey());
        values.put(ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_SETTING_KEY, settingKey);
        values.put(ChallengeSettingTableContract.ChallengeSettingTable.CHALLENGE_SETTING_VALUE, setting.getValue());
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
