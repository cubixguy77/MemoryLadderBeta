package speednumbers.mastersofmemory.challenges.domain.model;


import java.util.ArrayList;

import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.domain.model.Setting;

public class ChallengeFactory {

    public static Challenge CreateChallenge(long gameKey) {

        ArrayList<Setting> settings = new ArrayList<>();
        Setting digitsPerGroup = new Setting(-1, 1, 1, "Digits Per Group", 1, true);
        Setting memTimer = new Setting(-1, 2, 30, "Memorization Timer", 2, true);
        Setting recallTimer = new Setting(-1, 3, 60, "Recall Timer", 3, true);

        settings.add(digitsPerGroup);
        settings.add(memTimer);
        settings.add(recallTimer);

        Challenge challenge = new Challenge(gameKey, "42 Digits", false);
        challenge.setSettings(settings);

        return challenge;

    }

}
