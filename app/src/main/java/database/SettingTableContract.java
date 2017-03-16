package database;

import android.provider.BaseColumns;

public class SettingTableContract {
    public SettingTableContract() {}

    public static abstract class SettingTable implements BaseColumns {
        protected static final String TABLE_NAME = "Setting";
        protected static final String SETTING_SETTING_KEY = "Setting_Key";
        protected static final String SETTING_NAME = "Name";
        protected static final String SETTING_SORT_ORDER = "Sort_Order";
        protected static final String SETTING_VISIBLE = "Visible";
        protected static final String CREATE_SETTING_TABLE =
                "CREATE TABLE " + TABLE_NAME +
                "(" +
                    SETTING_SETTING_KEY + " INTEGER PRIMARY KEY," +
                    SETTING_NAME + " TEXT" + "," +
                    SETTING_SORT_ORDER + " INTEGER" + "," +
                    SETTING_VISIBLE + " INTEGER" +
                ")";
    }
}
