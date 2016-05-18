package speednumbers.mastersofmemory.com.domain.model;

public class Setting {
    private int challengeKey;
    private int settingKey;
    private int value;
    private String settingName;
    private int sortOrder;
    private boolean visible;

    public Setting(int challengeKey, int settingKey, int value, String settingName, int sortOrder, boolean visible) {
        this.challengeKey = challengeKey;
        this.settingKey = settingKey;
        this.value = value;
        this.settingName = settingName;
        this.sortOrder = sortOrder;
        this.visible = visible;
    }

    public int getChallengeKey() {
        return challengeKey;
    }

    public int getSettingKey() {
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
}
