package speednumbers.mastersofmemory.com.presentation;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitor;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Useful test methods common to all activities
 */
public class TestUtils {

    private static void rotateToLandscape(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private static void rotateToPortrait(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static void rotateOrientation(Activity activity) {
        int currentOrientation = activity.getResources().getConfiguration().orientation;

        switch (currentOrientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                rotateToPortrait(activity);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                rotateToLandscape(activity);
                break;
            default:
                rotateToLandscape(activity);
        }
    }

    /**
     * Gets an Activity in the RESUMED stage.
     * <p>
     * This method should never be called from the Main thread. In certain situations there might
     * be more than one Activities in RESUMED stage, but only one is returned.
     * See {@link ActivityLifecycleMonitor}.
     */
    public static Activity getCurrentActivity() throws IllegalStateException {
        // The array is just to wrap the Activity and be able to access it from the Runnable.
        final Activity[] resumedActivity = new Activity[1];

        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance()
                        .getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    resumedActivity[0] = (Activity) resumedActivities.iterator().next();
                } else {
                    throw new IllegalStateException("No Activity in stage RESUMED");
                }
            }
        });
        return resumedActivity[0];
    }

    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("position " + childPosition + " of parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) return false;
                ViewGroup parent = (ViewGroup) view.getParent();

                return parentMatcher.matches(parent)
                        && parent.getChildCount() > childPosition
                        && parent.getChildAt(childPosition).equals(view);
            }
        };
    }

    public static void clickAddChallengeButton() {
        onView(withId(R.id.action_add_challenge)).perform(click());
    }

    public static void expandCard(int position) {
        getNthChallengeSettingsExpandButton(position).perform(click());
    }

    /**
     * Performs actions necessary to add a new challenge
     * @param numDigits the number of digits the added challenge will have
     */
    public static void addChallenge(String numDigits) {
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
    public static void updateChallenge(int position, int digitsPerGroup, int digitSource) {
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
    public static void deleteChallenge(int position) {
        expandCard(position);
        getNthChallengeDeleteButton(position).perform(click());
    }

    /**
     * A custom {@link Matcher} which matches to the Nth challenge card in the list
     * @param position the position of the card within the ChallengeListContainer
     */
    public static Matcher<View> getNthChallengeCard(int position) {
        return nthChildOf(withId(R.id.ChallengeListContainer), position);
    }

    /**
     * A custom {@link Matcher} which matches to the Nth challenge's text label
     * @param position the position of the card within the ChallengeListContainer
     */
    public static ViewInteraction getNthChallengeTextField(int position) {
        return onView(allOf(isDescendantOfA(getNthChallengeCard(position)), withId(R.id.challengeText)));
    }

    /**
     * A custom {@link Matcher} which matches to the Nth challenge card's expand/contract button
     * @param position the position of the card within the ChallengeListContainer
     */
    public static ViewInteraction getNthChallengeSettingsExpandButton(int position) {
        return onView(allOf(isDescendantOfA(getNthChallengeCard(position)), withId(R.id.expandContractButton)));
    }

    /**
     * A custom {@link Matcher} which matches to the Nth challenge card's delete button
     * @param position the position of the card within the ChallengeListContainer
     */
    public static ViewInteraction getNthChallengeDeleteButton(int position) {
        return onView(allOf(isDescendantOfA(getNthChallengeCard(position)), withId(R.id.deleteButton)));
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
