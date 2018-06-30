package com.ikechukwuakalu.journalapp.add_entry;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ikechukwuakalu.journalapp.R;
import com.ikechukwuakalu.journalapp.base.BaseActivity;
import com.ikechukwuakalu.journalapp.data.JournalRepository;
import com.ikechukwuakalu.journalapp.data.local.LocalJournalDataSource;
import com.ikechukwuakalu.journalapp.utils.rx.RxScheduler;

public class AddEntryActivity extends BaseActivity {

    public static final String EDIT_TITLE = "EDIT_TITLE";
    public static final String EDIT_TEXT = "EDIT_TEXT";
    public static final String EDIT_ID = "EDIT_ID";
    public static final String EDIT_CREATED = "EDIT_CREATED";

    private AddEntryFragment fragment = null;
    private AddEntryPresenter presenter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        if (fragment == null) fragment = new AddEntryFragment();

        if (presenter == null) presenter = new AddEntryPresenter(
                new JournalRepository(new LocalJournalDataSource(getApplicationContext())),
                new RxScheduler()
        );

        addFragment(fragment, R.id.container);
    }

    public AddEntryPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (getIntent().getStringExtra(EDIT_TITLE) != null) return;

        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_down);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return false;
    }
}
