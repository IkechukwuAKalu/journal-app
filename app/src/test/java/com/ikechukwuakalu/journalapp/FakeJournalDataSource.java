package com.ikechukwuakalu.journalapp;

import com.ikechukwuakalu.journalapp.data.JournalDataSource;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class FakeJournalRepository implements JournalDataSource {

    private Map<String, JournalEntry> journalEntries = new HashMap<>();

    @Override
    public Completable add(JournalEntry journalEntry) {
        journalEntries.put(String.valueOf(journalEntry.getId()), journalEntry);
        return Completable.complete();
    }

    @Override
    public Observable<JournalEntry> getEntry(int id) {
        return Observable.just(journalEntries.get(String.valueOf(id)));
    }

    @Override
    public Observable<List<JournalEntry>> getEntries() {
        List<JournalEntry> entries = new ArrayList<>(journalEntries.values());
        return Observable.just(entries);
    }

    @Override
    public Observable<JournalEntry> edit(long id, JournalEntry updatedJournalEntry) {
        return null;
    }

    @Override
    public boolean remove(JournalEntry journalEntry) {
        journalEntries.remove(String.valueOf(journalEntry.getId()));
        return true;
    }
}
