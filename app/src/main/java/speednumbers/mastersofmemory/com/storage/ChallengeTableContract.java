package speednumbers.mastersofmemory.com.storage;

import android.provider.BaseColumns;

public class ChallengeTableContract {
    public ChallengeTableContract() {}

    public static abstract class ChallengeTable implements BaseColumns {
        protected static final String TABLE_NAME = "Challenge";
        protected static final String CHALLENGE_CHALLENGE_KEY = "Challenge_Key";
        protected static final String CHALLENGE_GAME_KEY = "Game_Key";
        protected static final String CHALLENGE_TITLE = "Title";
        protected static final String CHALLENGE_LOCKED = "Locked";
        protected static final String CREATE_CHALLENGE_TABLE =
                "CREATE TABLE " + TABLE_NAME +
                "(" +
                CHALLENGE_CHALLENGE_KEY + " INTEGER PRIMARY KEY," +
                CHALLENGE_GAME_KEY + " INTEGER REFERENCES " + GameTableContract.GameTable.TABLE_NAME + "," + // Define a foreign key
                CHALLENGE_TITLE + " TEXT" +
                CHALLENGE_LOCKED + " INTEGER" +
                ")";
    }
}
