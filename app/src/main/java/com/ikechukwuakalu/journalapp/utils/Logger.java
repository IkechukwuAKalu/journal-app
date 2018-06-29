package com.ikechukwuakalu.journalapp.utils;

import android.util.Log;

public class Logger {
    private static final String APP_TAG = "JournalApp";

    public static void debug(String msg) {
        Log.d(APP_TAG, msg);
    }

    public static void warn(String msg) {
        Log.w(APP_TAG, msg);
    }

    public static void error(String msg) {
        Log.e(APP_TAG, msg);
    }
}
