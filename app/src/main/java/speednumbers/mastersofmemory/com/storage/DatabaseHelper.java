package speednumbers.mastersofmemory.com.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper sInstance;

    // Database Info
    private static final String DATABASE_NAME = "MemoryDatabase";
    private static final int DATABASE_VERSION = 1;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GameTableContract.GameTable.CREATE_GAME_TABLE);
        db.execSQL(ChallengeTableContract.ChallengeTable.CREATE_CHALLENGE_TABLE);


        long gameKey = insertGame("Speed Numbers", "speedNumbersIcon.png");
        insertChallenge(gameKey, "10 Digits", false);
        insertChallenge(gameKey, "20 Digits", false);
        insertChallenge(gameKey, "30 Digits", false);
    }

    public long insertGame(String title, String iconPath) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        long gameKey = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(GameTableContract.GameTable.GAME_TITLE, title);
            values.put(GameTableContract.GameTable.GAME_ICON_PATH, iconPath);
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

    private long insertChallenge(long gameKey, String title, boolean locked) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        long challengeKey = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(ChallengeTableContract.ChallengeTable.CHALLENGE_GAME_KEY, gameKey);
            values.put(ChallengeTableContract.ChallengeTable.CHALLENGE_TITLE, title);
            values.put(ChallengeTableContract.ChallengeTable.CHALLENGE_LOCKED, locked ? 1 : 0);
            challengeKey = db.insert(ChallengeTableContract.ChallengeTable.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            Log.d("ERROR", "Error while trying to add challenge to database");
        } finally {
            db.endTransaction();
        }

        return challengeKey;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + GameTableContract.GameTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ChallengeTableContract.ChallengeTable.TABLE_NAME);
            onCreate(db);
        }
    }
}
