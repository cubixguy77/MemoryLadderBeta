package speednumbers.mastersofmemory.com.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Challenge {
    private long challengeKey;
    private long gameKey;
    private String title;
    private boolean locked;

    private List<Setting> settings;

    public Challenge(long challengeKey, long gameKey, String title, boolean locked) {
        this.challengeKey = challengeKey;
        this.gameKey = gameKey;
        this.title = title;
        this.locked = locked;
    }

    public Challenge(long gameKey, long challengeKey, String title, boolean locked, List<Setting> settings) {
        this.gameKey = gameKey;
        this.challengeKey = challengeKey;
        this.title = title;
        this.locked = locked;
        this.settings = settings;
        if (settings == null)
            settings = new ArrayList<>();
    }

    public long getChallengeKey() {
        return challengeKey;
    }

    public long getGameKey() {
        return gameKey;
    }

    public String getTitle() {
        return title;
    }

    public boolean isLocked() {
        return locked;
    }


    public void setSettings(List<Setting> settings) { this.settings = settings; }
    public List<Setting> getSettings() { return settings; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("*** Challenge ***"); sb.append(System.getProperty("line.separator"));
        sb.append("Title: " + title); sb.append(System.getProperty("line.separator"));
        sb.append("Game Key: " + gameKey); sb.append(System.getProperty("line.separator"));
        sb.append("Challenge Key: " + challengeKey); sb.append(System.getProperty("line.separator"));
        sb.append("Locked: " + (locked ? "True" : "False")); sb.append(System.getProperty("line.separator"));

        if (settings != null && settings.size() > 0) {
            for (Setting setting : settings) {
                sb.append(setting.toString());
            }
        }

        sb.append("*** End Challenge ***");

        return sb.toString();
    }
}
