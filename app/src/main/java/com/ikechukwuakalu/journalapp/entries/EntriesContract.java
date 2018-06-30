package com.ikechukwuakalu.journalapp.entries;

import android.app.Activity;

import com.ikechukwuakalu.journalapp.base.BasePresenter;
import com.ikechukwuakalu.journalapp.base.BaseView;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;
import com.ikechukwuakalu.journalapp.data.models.User;

import java.util.List;

public interface EntriesContract {

    interface View extends BaseView {
        void showSignOutSuccess();

        void showError(String message);

        void showAuthScreen();

        void showUserDetails(User user);

        void showNoEntryFound();

        void showEntries(List<JournalEntry> entries);
    }

    interface Presenter extends BasePresenter<View> {
        void deleteAllEntries();

        void signOutUser(Activity activity);
    }
}
