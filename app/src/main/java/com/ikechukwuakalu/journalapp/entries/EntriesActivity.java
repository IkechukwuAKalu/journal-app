package com.ikechukwuakalu.journalapp.entries;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ikechukwuakalu.journalapp.R;
import com.ikechukwuakalu.journalapp.base.BaseActivity;
import com.ikechukwuakalu.journalapp.data.JournalRepository;
import com.ikechukwuakalu.journalapp.data.local.LocalJournalDataSource;
import com.ikechukwuakalu.journalapp.utils.rx.RxScheduler;

public class EntriesActivity extends BaseActivity {

    EntriesFragment fragment = null;
    EntriesPresenter presenter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries);

        if (fragment == null) fragment = new EntriesFragment();

        if (presenter == null) presenter = new EntriesPresenter(
                getApplicationContext(),
                new JournalRepository(new LocalJournalDataSource(getApplicationContext())),
                new RxScheduler()
        );

        addFragment(fragment, R.id.container);
    }

    public EntriesPresenter getPresenter() {
        return presenter;
    }
}
