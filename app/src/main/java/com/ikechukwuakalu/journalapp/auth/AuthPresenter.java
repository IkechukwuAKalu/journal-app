package com.ikechukwuakalu.journalapp.auth;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.ikechukwuakalu.journalapp.data.models.User;
import com.ikechukwuakalu.journalapp.utils.Logger;
import com.ikechukwuakalu.journalapp.utils.UserSharedPreferenceHelper;

public class AuthPresenter implements AuthContract.Presenter {

    private GoogleSignInAccount account;
    private UserSharedPreferenceHelper spHelper;
    private AuthContract.View view = null;

    AuthPresenter(Context context) {
        this.spHelper = new UserSharedPreferenceHelper(context);
        this.account = GoogleSignIn.getLastSignedInAccount(context);
    }

    @Override
    public void performLogin(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            saveUserDetails(account);
            Logger.debug(spHelper.getUserDetails().toString());
            if (view != null) view.showLoginSuccess();
        } catch (ApiException e) {
            Logger.error(e.getMessage());
            if (view != null) {
                view.showLoginError("Unable to sign in; code:" + e.getStatusCode());
            }
        }
    }

    private void saveUserDetails(GoogleSignInAccount account) {
        String photoUrl = null;
        if (account.getPhotoUrl() != null) photoUrl = account.getPhotoUrl().toString();
        User user = new User(
                account.getId(),
                account.getDisplayName(),
                account.getEmail(),
                photoUrl
        );
        spHelper.signInUser(user);
    }

    @Override
    public void attach(AuthContract.View view) {
        this.view = view;
        checkForActiveAccount();
    }

    private void checkForActiveAccount() {
        if (account != null) {
            saveUserDetails(account);
        }
    }

    @Override
    public void detach() {
        if (this.view != null) this.view = null;
    }
}
