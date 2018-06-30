package com.ikechukwuakalu.journalapp.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ikechukwuakalu.journalapp.R;
import com.ikechukwuakalu.journalapp.base.BaseActivity;

public class AuthActivity extends BaseActivity {

    private AuthFragment fragment = null;
    private AuthPresenter presenter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (fragment == null) fragment = new AuthFragment();

        if (presenter == null) presenter = new AuthPresenter(getApplicationContext());

        addFragment(fragment, R.id.container);
    }

    public AuthPresenter getPresenter() {
        return presenter;
    }
}
