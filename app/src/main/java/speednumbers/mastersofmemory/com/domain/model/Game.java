package speednumbers.mastersofmemory.com.domain.model;

public class Game {
    private int gameKey;
    private String title;
    private String iconPath;

    public Game(int gameKey, String title, String iconFileName) {
        this.gameKey = gameKey;
        this.title = title;
        this.iconPath = iconFileName;
    }

    public int getGameKey() {
        return gameKey;
    }

    public String getTitle() {
        return title;
    }

    public String getIconPath() {
        return iconPath;
    }
}
