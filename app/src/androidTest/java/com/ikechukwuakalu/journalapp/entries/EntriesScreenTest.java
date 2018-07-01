package com.ikechukwuakalu.journalapp.entries;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ikechukwuakalu.journalapp.R;
import com.ikechukwuakalu.journalapp.add_entry.RecyclerViewAction;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EntriesScreenTest {

    @Rule public ActivityTestRule<EntriesActivity> activityTestRule = new ActivityTestRule<>(EntriesActivity.class);

    @Before
    public void setup() {
        IdlingRegistry.getInstance()
                .register(activityTestRule.getActivity().getIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance()
                .unregister(activityTestRule.getActivity().getIdlingResource());
    }

    @Test
    public void clickOnFab_OpenAddEntryScreen() {
        // click on FAB
        onView(withId(R.id.add_new_entry))
                .perform(click());
        // check UI elements are displayed
        onView(withId(R.id.add_entry_title))
                .check(matches(isDisplayed()));
        onView(withId(R.id.add_entry_text))
                .check(matches(isDisplayed()));
    }

    @Test
    public void createTestEntry_ClickOnDeleteAll() {
        // click on FAB
        onView(withId(R.id.add_new_entry))
                .perform(click());
        // add test dat to fields
        onView(withId(R.id.add_entry_title))
                .perform(typeText("Test title"));
        onView(withId(R.id.add_entry_text))
                .perform(typeText("A very short test text"));
        // click on the save menu icon
        openActionBarOverflowOrOptionsMenu(
                InstrumentationRegistry.getInstrumentation().getTargetContext()
        );
        onView(withId(R.id.action_save_entry))
                .perform(click());
        // click on menu and then delete
        openActionBarOverflowOrOptionsMenu(
                InstrumentationRegistry.getInstrumentation().getTargetContext()
        );
        onView(withId(R.id.action_delete_all))
                .perform(click());
        // click on 'yes' on the alert dialog
        onView(withText("YES"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        // check 'no items notice' is shown
        onView(withId(R.id.no_entry_tv))
                .check(matches(isDisplayed()));
    }

    @Test
    public void createTestEntry_ClickOnFirst_OpenDetailsScreen() {
        // click on FAB
        onView(withId(R.id.add_new_entry))
                .perform(click());
        // add test dat to fields
        onView(withId(R.id.add_entry_title))
                .perform(typeText("Test title"));
        onView(withId(R.id.add_entry_text))
                .perform(typeText("A very short test text"));
        // click on the save menu icon
        openActionBarOverflowOrOptionsMenu(
                InstrumentationRegistry.getInstrumentation().getTargetContext()
        );
        onView(withId(R.id.action_save_entry))
                .perform(click());
        // click on first item
        onView(withId(R.id.entries_rv))
                .perform(RecyclerViewActions.
                        actionOnItemAtPosition(0,
                                RecyclerViewAction.clickChildViewWithId(R.id.entry_title)));
        // check 'title' is shown
        onView(withId(R.id.entry_title))
                .check(matches(isDisplayed()));
    }
}
