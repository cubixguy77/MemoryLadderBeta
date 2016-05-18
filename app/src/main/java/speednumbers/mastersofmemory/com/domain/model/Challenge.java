package speednumbers.mastersofmemory.com.domain.model;

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

    public List<Setting> getSettings() { return settings; }
}
