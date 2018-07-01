package com.ikechukwuakalu.journalapp.add_entry;

import com.ikechukwuakalu.journalapp.FakeJournalDataSource;
import com.ikechukwuakalu.journalapp.data.JournalRepository;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;
import com.ikechukwuakalu.journalapp.utils.Logger;
import com.ikechukwuakalu.journalapp.utils.rx.ImmediateSchduler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class AddEntryPresenterTest {

    @Mock private AddEntryContract.View view;
    @Mock private Logger logger;

    private JournalRepository repository;
    private AddEntryPresenter presenter;
    private FakeJournalDataSource fakeRepo = new FakeJournalDataSource();
    private JournalEntry journalEntry = new JournalEntry(
            "My first day on the 7 days of code challenge",
            "I stumbled upon an email from Andela, describing the task to create this app...",
            "Monday today"
    );
    private final String ENTRY_ID = "12";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.repository = new JournalRepository(fakeRepo);
        repository.removeAll();
        journalEntry.setId(Integer.valueOf(ENTRY_ID));
    }

    @After
    public void tearDown() {
        presenter.detach();
    }

    @Test
    public void saveEntry() {
        // given a presenter and an attached view when id is null
        presenter = new AddEntryPresenter(
                repository,
                new ImmediateSchduler()
        );
        presenter.attach(view);
        // verify an error is shown
        presenter.saveEntry(journalEntry);
        Mockito.verify(view).showSaveSuccess();
    }

    @Test
    public void editEntry() {
        // given a presenter and an attached view when id is null
        presenter = new AddEntryPresenter(
                repository,
                new ImmediateSchduler()
        );
        presenter.attach(view);
        // verify an error is shown
        JournalEntry entry = new JournalEntry("New edited title", "some short text", "01/07/2018");
        entry.setId(Integer.valueOf(ENTRY_ID));
        presenter.editEntry(entry);
        Mockito.verify(view).showSaveSuccess();
    }
}
