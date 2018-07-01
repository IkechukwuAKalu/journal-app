package com.ikechukwuakalu.journalapp.utils;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class SignInUtil {

    private static GoogleSignInOptions signInOptions = null;

    public static GoogleSignInOptions getSignInOptions() {
        if (signInOptions == null) {
            signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestId()
                    .requestEmail()
                    .requestProfile()
                    .build();
        }
        return signInOptions;
    }
}
