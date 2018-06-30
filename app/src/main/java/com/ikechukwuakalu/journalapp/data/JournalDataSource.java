package com.ikechukwuakalu.journalapp.data;

import com.ikechukwuakalu.journalapp.data.local.JournalEntry;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface JournalDataSource {

    /**
     * Creates a new Journal entry
     * @param journalEntry the Journal Entry Object
     * @return void
     */
    Completable add(JournalEntry journalEntry);

    /**
     * Fetches Journal Entry by ID
     * @param id the Journal Entry id
     * @return the Journal Entry Object if found, else null
     */
    Observable<JournalEntry> getEntry(int id);

    /**
     * Fetches all Journal Entries
     * @return a List of Journal Entries if found, else an empty list
     */
    Observable<List<JournalEntry>> getEntries();

    /**
     * Modifies the content of a Journal entry
     * @param journalEntries the new Journal entry Object
     * @return void
     */
    Completable edit(JournalEntry... journalEntries);

    /**
     * Removes a Journal entry
     * @param journalEntry the Journal entry Object. Passing null removes EVERY entry
     * @return void
     */
    Completable remove(JournalEntry journalEntry);

    /**
     * Removes all Journal entries
     * @return void
     */
    Completable removeAll();
}
