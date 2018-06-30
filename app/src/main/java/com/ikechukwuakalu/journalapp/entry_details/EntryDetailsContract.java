package com.ikechukwuakalu.journalapp.entry_details;

import com.ikechukwuakalu.journalapp.base.BasePresenter;
import com.ikechukwuakalu.journalapp.base.BaseView;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;

public interface EntryDetailsContract {

    interface View extends BaseView {
        void showLoading();

        void hideLoading();

        void showError(String message);

        void showDeleteSuccess();

        void showEntryDetails(JournalEntry journalEntry);
    }

    interface Presenter extends BasePresenter<View> {
        void deleteEntry(JournalEntry journalEntry);
    }
}
