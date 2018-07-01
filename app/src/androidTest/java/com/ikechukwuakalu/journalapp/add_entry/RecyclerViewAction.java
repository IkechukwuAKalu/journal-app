package com.ikechukwuakalu.journalapp.add_entry;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

public class RecyclerViewAction {

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with a specified id";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View view1 = view.findViewById(id);
                view1.performClick();
            }
        };
    }
}
