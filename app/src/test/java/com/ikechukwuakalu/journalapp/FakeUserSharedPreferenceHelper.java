package com.ikechukwuakalu.journalapp;

import com.ikechukwuakalu.journalapp.data.models.User;

public class FakeUserSharedPreferenceHelper {

    public static User getUser() {
        return new User(
                "12",
                "Jane John Doe",
                "jane@john.doe",
                null
        );
    }
}
