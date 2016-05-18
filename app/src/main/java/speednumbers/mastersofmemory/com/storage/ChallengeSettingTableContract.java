package speednumbers.mastersofmemory.com.storage;

import android.provider.BaseColumns;

public class ChallengeSettingTableContract {
    public ChallengeSettingTableContract() {}

    public static abstract class ChallengeSettingTable implements BaseColumns {
        protected static final String TABLE_NAME = "Challenge_Setting";
        protected static final String CHALLENGE_SETTING_CHALLENGE_KEY = "Challenge_Key";
        protected static final String CHALLENGE_SETTING_SETTING_KEY = "Setting_Key";
        protected static final String CHALLENGE_SETTING_VALUE = "Value";
        protected static final String CREATE_CHALLENGE_SETTING_TABLE =
                "CREATE TABLE " + TABLE_NAME +
                "(" +
                    CHALLENGE_SETTING_CHALLENGE_KEY + " INTEGER PRIMARY KEY REFERENCES " + ChallengeTableContract.ChallengeTable.TABLE_NAME + "," +
                    CHALLENGE_SETTING_SETTING_KEY + " INTEGER PRIMARY KEY REFERENCES " + SettingTableContract.SettingTable.TABLE_NAME + "," +
                    CHALLENGE_SETTING_VALUE + " INTEGER" +
                ")";
    }
}
