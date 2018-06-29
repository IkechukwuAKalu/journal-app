package com.ikechukwuakalu.journalapp.data;

import com.ikechukwuakalu.journalapp.data.local.JournalEntry;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class JournalRepository implements JournalDataSource {

    private JournalDataSource dataSource;

    public JournalRepository(JournalDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Completable add(JournalEntry journalEntry) {
        return dataSource.add(journalEntry);
    }

    @Override
    public Observable<JournalEntry> getEntry(int id) {
        return dataSource.getEntry(id);
    }

    @Override
    public Observable<List<JournalEntry>> getEntries() {
        return dataSource.getEntries();
    }

    @Override
    public Observable<JournalEntry> edit(long id, JournalEntry updatedJournalEntry) {
        return dataSource.edit(id, updatedJournalEntry);
    }

    @Override
    public boolean remove(JournalEntry journalEntry) {
        return dataSource.remove(journalEntry);
    }
}
