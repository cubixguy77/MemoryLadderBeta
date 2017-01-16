package storage;

import android.provider.BaseColumns;

class ChallengeSettingTableContract {
    public ChallengeSettingTableContract() {}

    static abstract class ChallengeSettingTable implements BaseColumns {
        static final String TABLE_NAME = "Challenge_Setting";
        static final String CHALLENGE_SETTING_CHALLENGE_KEY = "Challenge_Key";
        static final String CHALLENGE_SETTING_SETTING_KEY = "Setting_Key";
        static final String CHALLENGE_SETTING_VALUE = "Value";
        static final String CREATE_CHALLENGE_SETTING_TABLE =
                "CREATE TABLE " + TABLE_NAME +
                "(" +
                    CHALLENGE_SETTING_CHALLENGE_KEY + " INTEGER REFERENCES " + ChallengeTableContract.ChallengeTable.TABLE_NAME + "," +
                    CHALLENGE_SETTING_SETTING_KEY + " INTEGER REFERENCES " + SettingTableContract.SettingTable.TABLE_NAME + "," +
                    CHALLENGE_SETTING_VALUE + " INTEGER" +
                ")";
    }
}
