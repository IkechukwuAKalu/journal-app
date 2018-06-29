package com.ikechukwuakalu.journalapp.add_entry;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ikechukwuakalu.journalapp.R;
import com.ikechukwuakalu.journalapp.base.BaseActivity;

public class AddEntryActivity extends BaseActivity {

    public static final String IS_EDIT = "is_edit";

    private AddEntryFragment fragment = null;
    private AddEntryPresenter presenter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        if (fragment == null) fragment = new AddEntryFragment();

        if (presenter == null) presenter = new AddEntryPresenter(getApplicationContext());

        addFragment(fragment, R.id.container);
    }

    public AddEntryPresenter getPresenter() {
        return presenter;
    }
}
