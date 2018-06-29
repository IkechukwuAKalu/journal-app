package com.ikechukwuakalu.journalapp.data;

import com.ikechukwuakalu.journalapp.data.local.JournalEntry;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface JournalDataSource {

    /**
     * Creates a new Journal entry
     * @param journalEntry the Journal Entry Object
     * @return true if it was successful, else false
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
     * @param id the Medication ID
     * @param updatedJournalEntry the new Journal entry Object
     * @return the updated Journal entry Object
     */
    Observable<JournalEntry> edit(long id, JournalEntry updatedJournalEntry);

    /**
     * Removes a Journal entry
     * @param journalEntry the Journal entry Object. Passing null removes EVERY entry
     * @return true if removal was successful, else false
     */
    boolean remove(JournalEntry journalEntry);
}
