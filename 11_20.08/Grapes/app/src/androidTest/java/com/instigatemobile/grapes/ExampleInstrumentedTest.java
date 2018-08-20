package com.instigatemobile.grapes;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.instigatemobile.grapes.activities.LogInActivity;
import com.instigatemobile.grapes.activities.PreviewActivity;
import com.squareup.spoon.Spoon;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<LogInActivity> mActivityRule = new ActivityTestRule<>(
            LogInActivity.class);

    @Test
    public void onIncorrectNickname() {
        onView(withId(R.id.nickname)).perform(typeText("ar"), closeSoftKeyboard());
        onView(withId(R.id.submit)).perform(click());
        onView(withId(R.id.submit)).check(matches(isDisplayed()));
    }

    @Test
    public void onIncorrectNickname1() {
        onView(withId(R.id.nickname)).perform(typeText("arm."), closeSoftKeyboard());
        onView(withId(R.id.submit)).perform(click());
        onView(withId(R.id.submit)).check(matches(isDisplayed()));
    }

    @Test
    public void onIncorrectNickname2() {
        onView(withId(R.id.nickname)).perform(typeText("arm_"), closeSoftKeyboard());
        onView(withId(R.id.submit)).perform(click());
        onView(withId(R.id.submit)).check(matches(isDisplayed()));
    }

    @Test
    public void onCorrectNickname() {
        onView(withId(R.id.nickname)).perform(typeText("Arman"), closeSoftKeyboard());
        onView(withId(R.id.submit)).perform(click());
        onView(withId(R.id.add_file_button)).check(matches(isDisplayed()));
        Spoon.screenshot(mActivityRule.getActivity(), "initial_state");
    }
}