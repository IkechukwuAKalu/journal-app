package com.ikechukwuakalu.journalapp;

import com.ikechukwuakalu.journalapp.data.JournalDataSource;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

public class FakeJournalDataSource implements JournalDataSource {

    private Map<String, JournalEntry> journalEntries = new HashMap<>();

    @Override
    public Completable add(final JournalEntry journalEntry) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() {
                journalEntries.put(String.valueOf(journalEntry.getId()), journalEntry);
            }
        });
    }

    @Override
    public Observable<JournalEntry> getEntry(int id) {
        return Observable.just(
                journalEntries.get(String.valueOf(id))
        );
    }

    @Override
    public Observable<List<JournalEntry>> getEntries() {
        List<JournalEntry> entries = new ArrayList<>(journalEntries.values());
        return Observable.just(entries);
    }

    @Override
    public Completable edit(final JournalEntry... entries) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() {
                for (JournalEntry entry : entries) {
                    if (journalEntries.containsKey(String.valueOf(entry.getId()))) {
                        JournalEntry entry1 = journalEntries.get(String.valueOf(entry.getId()));
                        entry1.setTitle(entry.getTitle());
                        entry1.setText(entry.getText());
                        journalEntries.remove(String.valueOf(entry.getId()));
                        journalEntries.put(String.valueOf(entry.getId()), entry1);
                    }
                }
            }
        });
    }

    @Override
    public Completable remove(final JournalEntry journalEntry) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() {
                journalEntries.remove(String.valueOf(journalEntry.getId()));
            }
        });
    }

    public Map<String, JournalEntry> getJournalEntries() {
        return journalEntries;
    }

    @Override
    public Completable removeAll() {
        return Completable.fromAction(new Action() {
            @Override
            public void run() {
                journalEntries.clear();
            }
        });
    }
}
