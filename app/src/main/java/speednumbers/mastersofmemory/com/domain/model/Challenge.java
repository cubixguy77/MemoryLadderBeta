package speednumbers.mastersofmemory.com.domain.model;

public class Challenge {
    private int challengeKey;
    private int gameKey;
    private String title;
    private boolean locked;

    public Challenge(int challengeKey, int gameKey, String title, boolean locked) {
        this.challengeKey = challengeKey;
        this.gameKey = gameKey;
        this.title = title;
        this.locked = locked;
    }

    public int getChallengeKey() {
        return challengeKey;
    }

    public int getGameKey() {
        return gameKey;
    }

    public String getTitle() {
        return title;
    }

    public boolean isLocked() {
        return locked;
    }
}
