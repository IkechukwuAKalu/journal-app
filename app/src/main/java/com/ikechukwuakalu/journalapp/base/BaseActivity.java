package com.ikechukwuakalu.journalapp.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.ikechukwuakalu.journalapp.utils.espresso.EspressoIdlingResource;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((BaseApplication) getApplication()).getRefWatcher()
//                .watch(this);
    }

    protected void addFragment(Fragment fragment, int fragmentId) {
        getSupportFragmentManager().beginTransaction()
                .add(fragmentId, fragment)
                .commit();
    }

    protected void replaceFragment(Fragment fragment, int fragmentId, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentId, fragment)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @VisibleForTesting
    public IdlingResource getIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
