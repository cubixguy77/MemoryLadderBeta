package speednumbers.mastersofmemory.com.presentation;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.InputType;
import android.view.View;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import database.DatabaseHelper;
import selectChallenge.viewChallengeList.ChallengeListActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static speednumbers.mastersofmemory.com.presentation.TestUtils.nthChildOf;

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
        addChallenge(CHALLENGE_10);

        // Verify title of added challenge
        getNthChallengeTextField(0).check(matches(withText(CHALLENGE_10_DIGITS)));
    }

    @Test
    public void updateChallenge() {
        // Add a new challenge
        addChallenge(CHALLENGE_20);

        // Update challenge settings
        updateChallenge(0, DIGITS_PER_GROUP_3, BINARY);

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
        addChallenge(CHALLENGE_30);

        // Delete the challenge
        deleteChallenge(0);

        // Verify it was deleted
        getNthChallengeTextField(0).check(matches(not(isDisplayed())));
    }

    private void clickAddChallengeButton() {
        onView(withId(R.id.action_add_challenge)).perform(click());
    }

    private void expandCard(int position) {
        getNthChallengeSettingsExpandButton(position).perform(click());
    }

    /**
     * Performs actions necessary to add a new challenge
     * @param numDigits the number of digits the added challenge will have
     */
    private void addChallenge(String numDigits) {
        // open dialog
        clickAddChallengeButton();

        // type number of digits
        onView(withInputType(InputType.TYPE_CLASS_NUMBER)).perform(typeText(numDigits), closeSoftKeyboard());

        // click ok
        onView(withId(android.R.id.button1)).perform(click());
    }

    /**
     * Performs actions necessary to update the settings of an existing challenge
     * @param position the position of the card to modify
     * @param digitsPerGroup the new value for the digits per group setting
     * @param digitSource the new value for the digit source setting
     */
    private void updateChallenge(int position, int digitsPerGroup, int digitSource) {
        // expand challenge to expose settings
        getNthChallengeSettingsExpandButton(position).perform(click());

        // click on digits per group setting
        onView(allOf(withId(R.id.digitGroupingContainer), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).perform(click());

        // update digits per group
        onData(anything()).inAdapterView(Matchers.allOf(withId(R.id.select_dialog_listview), nthChildOf(withId(R.id.contentPanel), 0))).atPosition(digitsPerGroup).perform(click());

        // click on digit source setting
        onView(allOf(withId(R.id.digitSourceContainer), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).perform(click());

        // update digits source
        onData(anything()).inAdapterView(Matchers.allOf(withId(R.id.select_dialog_listview), nthChildOf(withId(R.id.contentPanel), 0))).atPosition(digitSource).perform(click());
    }

    /**
     * Performs actions necessary to delete a challenge
     * @param position the position of the card to be deleted
     */
    private void deleteChallenge(int position) {
        expandCard(position);
        getNthChallengeDeleteButton(position).perform(click());
    }

    /**
     * A custom {@link Matcher} which matches to the Nth challenge card in the list
     * @param position the position of the card within the ChallengeListContainer
     */
    private Matcher<View> getNthChallengeCard(int position) {
        return nthChildOf(withId(R.id.ChallengeListContainer), position);
    }

    /**
     * A custom {@link Matcher} which matches to the Nth challenge's text label
     * @param position the position of the card within the ChallengeListContainer
     */
    private ViewInteraction getNthChallengeTextField(int position) {
        return onView(allOf(isDescendantOfA(getNthChallengeCard(position)), withId(R.id.challengeText)));
    }

    /**
     * A custom {@link Matcher} which matches to the Nth challenge card's expand/contract button
     * @param position the position of the card within the ChallengeListContainer
     */
    private ViewInteraction getNthChallengeSettingsExpandButton(int position) {
        return onView(allOf(isDescendantOfA(getNthChallengeCard(position)), withId(R.id.expandContractButton)));
    }

    /**
     * A custom {@link Matcher} which matches to the Nth challenge card's delete button
     * @param position the position of the card within the ChallengeListContainer
     */
    private ViewInteraction getNthChallengeDeleteButton(int position) {
        return onView(allOf(isDescendantOfA(getNthChallengeCard(position)), withId(R.id.deleteButton)));
    }
}