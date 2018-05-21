package speednumbers.mastersofmemory.com.presentation;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import database.DatabaseHelper;
import selectChallenge.viewChallengeList.ChallengeListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static speednumbers.mastersofmemory.com.presentation.TestUtils.clickAddChallengeButton;
import static speednumbers.mastersofmemory.com.presentation.TestUtils.getNthChallengeTextField;

@RunWith(AndroidJUnit4.class)
public class ChallengeListTest {

    private static final String CHALLENGE_10 = "10";
    private static final String CHALLENGE_10_DIGITS = "10 Digits";

    private static final String CHALLENGE_20 = "20";
    private static final String CHALLENGE_20_DIGITS = "20 Digits";

    private static final String CHALLENGE_30 = "30";

    private static final int DIGITS_PER_GROUP_3 = 2;
    private static final String DIGITS_PER_GROUP_3_Digits = "3 Digits";

    private static final int BINARY = 2;
    private static final String BINARY_TEXT = "Binary";

    @Rule
    public ActivityTestRule<ChallengeListActivity> mActivityRule = new ActivityTestRule<ChallengeListActivity>(ChallengeListActivity.class) {

        /**
         * For the sake of consistency between tests, delete all challenges before beginning
         */
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            DatabaseHelper db = new DatabaseHelper();
            db.deleteAllChallenges();
        }
    };

    @Test
    public void clickAddChallengeButton_opensAddChallengeDialog() {
        // Click on the add task button
        clickAddChallengeButton();

        // Check if the "New Challenge" dialog is displayed
        onView(withText(R.string.newChallenge))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test
    public void addChallenge() {
        // Add a new challenge
        TestUtils.addChallenge(CHALLENGE_10);

        // Verify title of added challenge
        getNthChallengeTextField(0).check(matches(withText(CHALLENGE_10_DIGITS)));
    }

    @Test
    public void updateChallenge() {
        // Add a new challenge
        TestUtils.addChallenge(CHALLENGE_20);

        // Update challenge settings
        TestUtils.updateChallenge(0, DIGITS_PER_GROUP_3, BINARY);

        // Verify title of added challenge
        getNthChallengeTextField(0).check(matches(withText(CHALLENGE_20_DIGITS)));

        // Verify digits per group was modified to 3
        onView(allOf(withId(R.id.digitsPerGroupValue), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).check(matches(withText(DIGITS_PER_GROUP_3_Digits)));

        // Verify digit source was modified to binary
        onView(allOf(withId(R.id.digitSourceValue), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).check(matches(withText(BINARY_TEXT)));
    }

    @Test
    public void deleteChallenge() {
        // Add a new challenge
        TestUtils.addChallenge(CHALLENGE_30);

        // Delete the challenge
        TestUtils.deleteChallenge(0);

        // Verify it was deleted
        getNthChallengeTextField(0).check(matches(not(isDisplayed())));
    }
}