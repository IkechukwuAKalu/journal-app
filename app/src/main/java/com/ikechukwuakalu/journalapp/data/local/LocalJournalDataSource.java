package com.ikechukwuakalu.journalapp.data.local;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.ikechukwuakalu.journalapp.data.JournalDataSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

public class LocalJournalDataSource implements JournalDataSource{

    private JournalEntryDao entryDao;

    public LocalJournalDataSource(Context context) {
        JournalDatabase database = Room.databaseBuilder(context,
                JournalDatabase.class,
                "journal-db")
                .build();
        entryDao = database.journalEntryDao();
    }

    @Override
    public Completable add(final JournalEntry journalEntry) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() {
                entryDao.add(journalEntry);
            }
        });
    }

    @Override
    public Observable<JournalEntry> getEntry(int id) {
        return entryDao.getById(id)
                .toObservable();
    }

    @Override
    public Observable<List<JournalEntry>> getEntries() {
        return entryDao.getAll()
                .toObservable();
    }

    @Override
    public Completable edit(final JournalEntry... journalEntries) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() {
                entryDao.edit(journalEntries);
            }
        });
    }

    @Override
    public Completable remove(final JournalEntry journalEntry) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() {
                entryDao.remove(journalEntry);
            }
        });
    }

    @Override
    public Completable removeAll() {
        return Completable.fromAction(new Action() {
            @Override
            public void run() {
                entryDao.removeAll();
            }
        });
    }
}
