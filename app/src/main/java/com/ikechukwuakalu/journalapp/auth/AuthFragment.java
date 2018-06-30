package com.ikechukwuakalu.journalapp.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.ikechukwuakalu.journalapp.R;
import com.ikechukwuakalu.journalapp.base.BaseFragment;
import com.ikechukwuakalu.journalapp.entries.EntriesActivity;
import com.ikechukwuakalu.journalapp.utils.SigninUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AuthFragment extends BaseFragment implements AuthContract.View {

    private final int SIGN_IN_CODE = 101;

    @BindView(R.id.sign_in_button) SignInButton signInButton;

    private Unbinder unbinder;

    private GoogleSignInClient signInClient;

    private AuthActivity activity;

    private AuthPresenter presenter = null;

    public AuthFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ((AuthActivity) getActivity());
        if (activity != null) presenter = activity.getPresenter();
        setupSignIn();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        unbinder = ButterKnife.bind(this, view);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        return view;
    }

    private void setupSignIn() {
        Context context = getContext();
        if (context != null) signInClient = GoogleSignIn.getClient(context, SigninUtil.getSignInOptions());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) presenter.attach(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) presenter.detach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.sign_in_button)
    public void signIn() {
        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (presenter != null) {
                presenter.attach(this);
                presenter.performLogin(task);
            }
        }
    }

    @Override
    public void showLoginError(String message) {
        showLongToast(getContext(), message);
    }

    @Override
    public void showLoginSuccess() {
        showShortToast(getContext(), "Login successful");
        startActivity(new Intent(getContext(), EntriesActivity.class));
        activity.finish();
    }
}
