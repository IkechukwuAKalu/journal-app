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
    public Observable<JournalEntry> edit(long id, JournalEntry updatedJournalEntry) {
        return null;
    }

    @Override
    public boolean remove(JournalEntry journalEntry) {
        entryDao.remove(journalEntry);
        return true;
    }
}
