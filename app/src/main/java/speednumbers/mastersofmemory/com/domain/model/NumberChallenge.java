package speednumbers.mastersofmemory.com.domain.model;

public class NumberChallenge {

    private static final int DIGITS_PER_GROUP_INDEX = 0;
    private static final int MEM_TIMER_INDEX = 1;
    private static final int RECALL_TIMER_INDEX = 2;

    public static Setting getDigitsPerGroupSetting(Challenge challenge) {
        return getSetting(challenge, DIGITS_PER_GROUP_INDEX);
    }

    public static Setting getMemTimerSetting(Challenge challenge) {
        return getSetting(challenge, MEM_TIMER_INDEX);
    }

    public static Setting getRecallTimerSetting(Challenge challenge) {
        return getSetting(challenge, RECALL_TIMER_INDEX);
    }

    public static Setting getSetting(Challenge challenge, int index) {
        if (challenge.getSettings() == null || challenge.getSettings().size() <= index)
            return null;
        return challenge.getSettings().get(index);
    }
}
