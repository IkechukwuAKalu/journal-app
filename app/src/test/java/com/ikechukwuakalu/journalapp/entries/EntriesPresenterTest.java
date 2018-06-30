package com.ikechukwuakalu.journalapp.entries;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.ikechukwuakalu.journalapp.FakeJournalDataSource;
import com.ikechukwuakalu.journalapp.data.JournalRepository;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;
import com.ikechukwuakalu.journalapp.utils.UserSharedPreferenceHelper;
import com.ikechukwuakalu.journalapp.utils.rx.ImmediateSchduler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

public class EntriesPresenterTest {

    @Mock private EntriesContract.View view;
    @Mock private Context context;

    private JournalRepository journalRepository;
    private FakeJournalDataSource fakeJournalDataSource;

    private JournalEntry journalEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        fakeJournalDataSource = new FakeJournalDataSource();
        journalRepository = new JournalRepository(fakeJournalDataSource);
        journalEntry = new JournalEntry(
                "My first day on the 7 days of code challenge",
                "I stumbled upon an email from Andela, describing the task to create this app...",
                "Monday today"
        );
        journalEntry.setId(1);
        EntriesPresenter presenter = new EntriesPresenter(

        );
        presenter.attach(view);
    }

    @Test
    public void loadEntries_CheckItemsShown() {
        for (int i = 0; i < 4; i++) {
            journalRepository.add(journalEntry);
        }
        List<JournalEntry> entries = new ArrayList<>(fakeJournalDataSource.getJournalEntries().values());
        verify(view).showEntries(entries);
    }
}
