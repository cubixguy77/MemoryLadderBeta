package speednumbers.mastersofmemory.challenges.domain.model;

import java.util.ArrayList;

import speednumbers.mastersofmemory.challenges.MyApplication;
import speednumbers.mastersofmemory.com.presentation.R;

public class ChallengeFactory {

    public static Challenge CreateChallenge(long gameKey, int numDigits) {

        ArrayList<Setting> settings = new ArrayList<>();
        Setting numDigitsSetting = new Setting(-1, 1, numDigits, "Number of Digits", 10, true);
        Setting digitsPerGroup = new Setting(-1, 2, 1, "Digits Per Group", 20, true);
        Setting memTimer = new Setting(-1, 3, 30, "Memorization Timer", 30, true);
        Setting recallTimer = new Setting(-1, 4, 60, "Recall Timer", 40, true);

        settings.add(numDigitsSetting);
        settings.add(digitsPerGroup);
        settings.add(memTimer);
        settings.add(recallTimer);

        Challenge challenge = new Challenge(gameKey, MyApplication.getAppContext().getResources().getString(R.string.challengeList_numDigits, numDigits), false);
        challenge.setSettings(settings);

        return challenge;
    }

}
