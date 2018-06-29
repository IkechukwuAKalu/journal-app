package com.ikechukwuakalu.journalapp.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface JournalEntryDao {

    @Insert
    void add(JournalEntry journalEntry);

    @Query("SELECT * FROM journalentry")
    Flowable<List<JournalEntry>> getAll();

    @Query("SELECT * FROM journalentry WHERE id = :id")
    Flowable<JournalEntry> getById(int id);

    @Update
    void edit(JournalEntry oldEntry, JournalEntry newEntry);

    @Delete
    void remove(JournalEntry journalEntry);
}
