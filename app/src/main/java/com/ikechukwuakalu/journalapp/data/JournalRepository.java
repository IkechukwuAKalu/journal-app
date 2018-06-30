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
    public Completable edit(JournalEntry... journalEntries) {
        return dataSource.edit(journalEntries);
    }

    @Override
    public Completable remove(JournalEntry journalEntry) {
        return dataSource.remove(journalEntry);
    }

    @Override
    public Completable removeAll() {
        return dataSource.removeAll();
    }
}
