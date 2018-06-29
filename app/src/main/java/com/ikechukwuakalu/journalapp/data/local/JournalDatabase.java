package com.ikechukwuakalu.journalapp.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = { JournalEntry.class }, version = 1)
public abstract class JournalDatabase extends RoomDatabase {

    public abstract JournalEntryDao journalEntryDao();
}
