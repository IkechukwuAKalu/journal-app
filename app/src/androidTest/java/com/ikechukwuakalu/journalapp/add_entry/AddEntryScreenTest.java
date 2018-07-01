package com.ikechukwuakalu.journalapp.add_entry;

import android.content.Intent;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ikechukwuakalu.journalapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class AddEntryScreenTest {

    @Rule public IntentsTestRule<AddEntryActivity> intentsTestRule =
            new IntentsTestRule<>(AddEntryActivity.class, true, false);

    @Before
    public void setup() {
        intentsTestRule.launchActivity(new Intent());
        IdlingRegistry.getInstance()
                .register(intentsTestRule.getActivity().getIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance()
                .unregister(intentsTestRule.getActivity().getIdlingResource());
    }

    @Test
    public void checkEditTextsAreShown() {
        // check UI elements are displayed
        onView(withId(R.id.add_entry_title))
                .check(matches(isDisplayed()));
        onView(withId(R.id.add_entry_text))
                .check(matches(isDisplayed()));
    }
}
