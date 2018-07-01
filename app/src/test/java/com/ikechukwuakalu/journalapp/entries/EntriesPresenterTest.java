package com.ikechukwuakalu.journalapp.entries;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.ikechukwuakalu.journalapp.FakeJournalDataSource;
import com.ikechukwuakalu.journalapp.FakeUserSharedPreferenceHelper;
import com.ikechukwuakalu.journalapp.data.JournalRepository;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;
import com.ikechukwuakalu.journalapp.data.models.User;
import com.ikechukwuakalu.journalapp.utils.UserSharedPreferenceHelper;
import com.ikechukwuakalu.journalapp.utils.rx.BaseScheduler;
import com.ikechukwuakalu.journalapp.utils.rx.ImmediateSchduler;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EntriesPresenterTest {

    @Mock private EntriesContract.View view;
    @Mock private GoogleSignInClient signInClient;
    @Mock private UserSharedPreferenceHelper spHelper;

    private final User user = FakeUserSharedPreferenceHelper.getUser();

    private EntriesPresenter presenter;
    private JournalRepository journalRepository;
    private FakeJournalDataSource fakeJournalDataSource;

    private JournalEntry journalEntry = new JournalEntry(
            "My first day on the 7 days of code challenge",
            "I stumbled upon an email from Andela, describing the task to create this app...",
            "Monday today"
    );

    private BaseScheduler scheduler = new ImmediateSchduler();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        setupContext();
        journalEntry.setId(1);
        fakeJournalDataSource = new FakeJournalDataSource();
        journalRepository = new JournalRepository(fakeJournalDataSource);
        presenter = new EntriesPresenter(
                journalRepository,
                spHelper,
                signInClient,
                scheduler
        );
    }

    private void setupContext() {
        when(spHelper.isUserSignedIn()).thenReturn(true);
        when(spHelper.getUserDetails()).thenReturn(user);
    }

    @After
    public void tearDown() {
        presenter.detach();
    }

    @Test
    public void check_UserDetailsIsShown() {
        // given an attached view
        presenter.attach(view);
        // verify that user details are shown
        verify(view).showUserDetails(user);
    }

    @Test @Ignore
    public void loadEntries_CheckItemsShown() {
        // with loaded entries
        journalRepository.add(journalEntry);
        // given an attached view
        presenter.attach(view);
        // verify entries are shown
        List<JournalEntry> entries = new ArrayList<>();
        entries.add(journalEntry);
        verify(view).showEntries(entries);
    }
}
