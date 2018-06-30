package com.ikechukwuakalu.journalapp.data.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class JournalEntry {

    @PrimaryKey(autoGenerate = true) private int id;
    @ColumnInfo(name = "title") private String title;
    @ColumnInfo(name = "text") private String text;
    @ColumnInfo(name = "created_at") private String createdAt;

    JournalEntry() {}

    @Ignore
    public JournalEntry(String title, String text, String createdAt) {
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
    }

    @Ignore
    public JournalEntry(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JournalEntry)) return false;
        JournalEntry journalEntry = (JournalEntry) o;
        return ((getTitle().equals(journalEntry.getTitle())) &&
                (getText().equals(journalEntry.getText())) &&
                (getCreatedAt().equals(journalEntry.getCreatedAt())));
    }

    @Override
    public String toString() {
        return "JournalEntry(id: " + getId() +
                " title: " + getTitle() +
                " text: " + getText() +
                " createdAt: " + getCreatedAt() + ")";
    }
}
