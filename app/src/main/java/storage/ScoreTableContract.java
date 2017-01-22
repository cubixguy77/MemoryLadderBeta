package storage;

import android.provider.BaseColumns;

class ScoreTableContract {
    public ScoreTableContract() {}

    static abstract class ScoreTable implements BaseColumns {
        static final String TABLE_NAME = "Score";
        static final String SCORE_KEY = "Score_Key";
        static final String CHALLENGE_KEY = "Challenge_Key";
        static final String SCORE = "Score";
        static final String MEM_TIME = "Mem_Time";
        static final String DATE_TIME = "Date_Time";
        static final String CREATE_SCORE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                        SCORE_KEY + " INTEGER PRIMARY KEY" + "," +
                        CHALLENGE_KEY + " INTEGER REFERENCES " + ChallengeTableContract.ChallengeTable.TABLE_NAME + "," +
                        SCORE + " INTEGER" + "," +
                        MEM_TIME + " INTEGER" + "," +
                        DATE_TIME + " INTEGER" +
                ")";
    }
}
