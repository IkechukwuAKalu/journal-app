package com.ikechukwuakalu.journalapp.entry_details;

import com.ikechukwuakalu.journalapp.FakeJournalDataSource;
import com.ikechukwuakalu.journalapp.data.JournalRepository;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;
import com.ikechukwuakalu.journalapp.utils.rx.BaseScheduler;
import com.ikechukwuakalu.journalapp.utils.rx.ImmediateSchduler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.functions.Action;

public class EntryDetailsPresenterTest {

    @Mock private EntryDetailsContract.View view;

    private JournalRepository repository;
    private EntryDetailsPresenter presenter;
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
    public void fetchEntryWithNoId() {
        // given a presenter and an attached view when id is null
        presenter = new EntryDetailsPresenter(
                repository,
                null,
                new ImmediateSchduler()
        );
        presenter.attach(view);
        // verify an error is shown
        presenter.fetchDetails();
        Mockito.verify(view).showError(EntryDetailsPresenter.MSG_ID_IS_NULL);
    }

    @Test
    public void fetchEntryWithValidId() {
        BaseScheduler scheduler = new ImmediateSchduler();
        // given a presenter and an attached view when id is null
        presenter = new EntryDetailsPresenter(
                repository,
                ENTRY_ID,
                scheduler
        );
        presenter.attach(view);
        // when entry is added to the repository
        repository.add(journalEntry)
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.ui())
        .subscribe(new Action() {
            @Override
            public void run() {
                // verify an entry is retrieved
                presenter.fetchDetails();
                Mockito.verify(view).hideLoading();
                Mockito.verify(view).showEntryDetails(journalEntry);
            }
        });
    }

    @Test
    public void fetchEntryWithInvalidId() {
        BaseScheduler scheduler = new ImmediateSchduler();
        // given a presenter and an attached view when id is null
        presenter = new EntryDetailsPresenter(
                repository,
                "16",
                scheduler
        );
        presenter.attach(view);
        // when entry is added to the repository
        repository.add(journalEntry)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        // verify an entry is retrieved
                        try {
                            presenter.fetchDetails();
                            Mockito.verify(view).hideLoading();
                            Mockito.verify(view).showError(EntryDetailsPresenter.MSG_ENTRY_NOT_FOUND);
                        } catch (Exception ignored) {}
                    }
                });
    }

    @Test
    public void deleteEntryWithValidId() {
        // given a presenter and an attached view when id is null
        presenter = new EntryDetailsPresenter(
                repository,
                ENTRY_ID,
                new ImmediateSchduler()
        );
        presenter.attach(view);
        // when entry is added to the repository
        repository.add(journalEntry);
        // verify the entry is deleted
        presenter.deleteEntry(journalEntry);
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).showDeleteSuccess();
    }
}
