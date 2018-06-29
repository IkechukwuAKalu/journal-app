package com.ikechukwuakalu.journalapp.entry_details;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ikechukwuakalu.journalapp.R;
import com.ikechukwuakalu.journalapp.base.BaseActivity;

public class EntryDetailsActivity extends BaseActivity {

    public static final String ENTRY_ID = "entry_id";

    private String entryId = null;

    private EntryDetailsFragment fragment = null;
    private EntryDetailsPresenter presenter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_detail);

        if (fragment == null) fragment = new EntryDetailsFragment();

        entryId = getIntent().getStringExtra(ENTRY_ID);
        if (presenter == null) presenter = new EntryDetailsPresenter(getApplicationContext(), entryId);

        addFragment(fragment, R.id.container);
    }

    public EntryDetailsPresenter getPresenter() {
        return presenter;
    }

    public String getEntryId() {
        return entryId;
    }
}
