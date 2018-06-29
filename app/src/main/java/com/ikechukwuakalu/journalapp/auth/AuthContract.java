package com.ikechukwuakalu.journalapp.auth;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.ikechukwuakalu.journalapp.base.BasePresenter;
import com.ikechukwuakalu.journalapp.base.BaseView;

public interface AuthContract {

    interface View extends BaseView {
        void showLoginError(String message);

        void showLoginSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void performLogin(Task<GoogleSignInAccount> task);
    }
}
