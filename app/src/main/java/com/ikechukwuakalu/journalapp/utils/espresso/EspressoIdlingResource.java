package com.ikechukwuakalu.journalapp.utils.espresso;

import android.support.test.espresso.IdlingResource;

public class EspressoIdlingResource {

    private static SimpleCountingResource countingResource = new SimpleCountingResource("MY_JOURNAL_APP");

    public static void increment() {
        countingResource.increment();
    }

    public static void decrement() {
        countingResource.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return countingResource;
    }
}
