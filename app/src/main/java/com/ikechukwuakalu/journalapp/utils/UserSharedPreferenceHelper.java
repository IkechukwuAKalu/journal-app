package com.ikechukwuakalu.journalapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.ikechukwuakalu.journalapp.data.models.User;

public class UserSharedPreferenceHelper {

    private final String USER_ID = "user_id";
    private final String USER_NAME = "user_name";
    private final String USER_EMAIL = "user_email";
    private final String USER_PHOTO = "user_photo";

    private SharedPreferences preferences;

    public UserSharedPreferenceHelper(Context context) {
        String SP_NAME = "My-Journal-App-SP";
        preferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public boolean isUserSignedIn() {
        return preferences.getString(USER_ID, null) != null;
    }

    public void signInUser(User user) {
        SharedPreferences.Editor editor = preferences.edit();
        addUserDetails(user, editor);
        editor.apply();
    }

    private void addUserDetails(@NonNull User user, @NonNull SharedPreferences.Editor editor) {
        editor.putString(USER_ID, user.getId());
        editor.putString(USER_NAME, user.getName());
        editor.putString(USER_EMAIL, user.getEmail());
        editor.putString(USER_PHOTO, user.getPhotoUri());
    }

    public User getUserDetails() {
        String id = preferences.getString(USER_ID, "");
        String name = preferences.getString(USER_NAME, "");
        String email = preferences.getString(USER_EMAIL, "");
        String photo = preferences.getString(USER_PHOTO, "");

        return new User(id, name, email, photo);
    }

    public void signUserOut() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}