package speednumbers.mastersofmemory.challenges.domain.model;

public class ChallengeSetting {
    private long challengeKey;
    private long settingKey;
    private int value;
    private String settingName;
    private int sortOrder;
    private boolean visible;

    public ChallengeSetting(int challengeKey, int settingKey, int value, String settingName, int sortOrder, boolean visible) {
        this.challengeKey = challengeKey;
        this.settingKey = settingKey;
        this.value = value;
        this.settingName = settingName;
        this.sortOrder = sortOrder;
        this.visible = visible;
    }

    public long getChallengeKey() {
        return challengeKey;
    }

    public long getSettingKey() {
        return settingKey;
    }

    public int getValue() {
        return value;
    }

    public String getSettingName() {
        return settingName;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setChallengeKey(long challengeKey) {
        this.challengeKey = challengeKey;
    }
}
