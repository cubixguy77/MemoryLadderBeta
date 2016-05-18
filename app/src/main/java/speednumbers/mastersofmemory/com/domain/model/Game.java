package speednumbers.mastersofmemory.com.domain.model;

public class Game {
    private long gameKey;
    private String title;
    private String iconPath;

    public Game(long gameKey, String title, String iconFileName) {
        this.gameKey = gameKey;
        this.title = title;
        this.iconPath = iconFileName;
    }

    public Game(String title, String iconFileName) {
        this.title = title;
        this.iconPath = iconFileName;
    }

    public long getGameKey() {
        return gameKey;
    }

    public String getTitle() {
        return title;
    }

    public String getIconPath() {
        return iconPath;
    }
}
