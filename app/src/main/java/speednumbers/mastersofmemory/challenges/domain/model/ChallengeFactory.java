package speednumbers.mastersofmemory.challenges.domain.model;

import java.util.ArrayList;

public class ChallengeFactory {

    public static Challenge CreateChallenge(long gameKey) {

        ArrayList<Setting> settings = new ArrayList<>();
        Setting numDigits = new Setting(-1, 1, 25, "Number of Digits", 10, true);
        Setting digitsPerGroup = new Setting(-1, 2, 1, "Digits Per Group", 20, true);
        Setting memTimer = new Setting(-1, 3, 30, "Memorization Timer", 30, true);
        Setting recallTimer = new Setting(-1, 4, 60, "Recall Timer", 40, true);

        settings.add(numDigits);
        settings.add(digitsPerGroup);
        settings.add(memTimer);
        settings.add(recallTimer);

        Challenge challenge = new Challenge(gameKey, "25 Digits", false);
        challenge.setSettings(settings);

        return challenge;
    }

}