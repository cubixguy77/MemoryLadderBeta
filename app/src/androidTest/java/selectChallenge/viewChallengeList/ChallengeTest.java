package selectChallenge.viewChallengeList;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import speednumbers.mastersofmemory.com.presentation.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static speednumbers.mastersofmemory.com.presentation.TestUtils.addChallenge;
import static speednumbers.mastersofmemory.com.presentation.TestUtils.getNthChallengeTextField;
import static speednumbers.mastersofmemory.com.presentation.TestUtils.sleep;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ChallengeTest {

    @Rule
    public ActivityTestRule<ChallengeListActivity> mActivityTestRule = new ActivityTestRule<>(ChallengeListActivity.class);

    @Test
    public void challengeTest() {
        addChallenge("20");
        getNthChallengeTextField(0).perform(click());

        sleep(200);

        onView(withId(R.id.nextGroupButton)).perform(click());
        onView(withId(R.id.nextGroupButton)).perform(click());

        onView(withId(R.id.action_submit_memorization)).perform(click());

        onView(withId(R.id.key_0)).perform(click());
        onView(withId(R.id.key_1)).perform(click());
        onView(withId(R.id.key_2)).perform(click());
        onView(withId(R.id.key_3)).perform(click());
        onView(withId(R.id.key_4)).perform(click());
        onView(withId(R.id.key_5)).perform(click());
        onView(withId(R.id.backSpaceButton)).perform(click());
        onView(withId(R.id.backSpaceButton)).perform(click());
        onView(withId(R.id.backSpaceButton)).perform(click());
        onView(withId(R.id.key_4)).perform(click());
        onView(withId(R.id.key_6)).perform(click());

        onView(withId(R.id.action_submit_recall)).perform(click());

        sleep(200);

        onView(withId(R.id.accuracyText)).check(matches(withText("83%")));
        onView(withId(R.id.scoreText)).check(matches(withText("5/6")));
    }
}
