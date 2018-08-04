package com.example.toshiba.pushnotificationusingfirebace.Model;

import android.support.annotation.NonNull;

public class UserId {
    public String userId;

    public <T extends UserId>T  withId(@NonNull final  String userId) {
        this.userId = userId;
        return (T)this;
    }
}
