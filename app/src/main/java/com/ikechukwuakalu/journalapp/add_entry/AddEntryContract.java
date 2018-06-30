package com.ikechukwuakalu.journalapp.add_entry;

import com.ikechukwuakalu.journalapp.base.BasePresenter;
import com.ikechukwuakalu.journalapp.base.BaseView;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;

public interface AddEntryContract {

    interface View extends BaseView {
        void showSaveSuccess();

        void showSaveError(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void saveEntry(JournalEntry journalEntry);

        void editEntry(JournalEntry journalEntry);
    }
}
