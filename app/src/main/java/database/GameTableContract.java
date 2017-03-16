package database;

import android.provider.BaseColumns;

public class GameTableContract {
    public GameTableContract() {}

    public static abstract class GameTable implements BaseColumns {
        protected static final String TABLE_NAME = "Game";
        protected static final String GAME_GAME_KEY = "Game_Key";
        protected static final String GAME_TITLE = "Title";
        protected static final String GAME_ICON_PATH = "Icon_Path";
        protected static final String CREATE_GAME_TABLE =
                "CREATE TABLE " + TABLE_NAME +
                "(" +
                    GAME_GAME_KEY + " INTEGER PRIMARY KEY" + "," +
                    GAME_TITLE + " TEXT" + "," +
                    GAME_ICON_PATH + " TEXT" +
                ")";
    }
}
