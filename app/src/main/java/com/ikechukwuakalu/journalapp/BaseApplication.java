package com.ikechukwuakalu.journalapp;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class BaseApplication extends Application {

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        // installLeakCanary();
    }

    private void installLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(getApplicationContext())) return;
        refWatcher = LeakCanary.install(this);
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }
}
