package com.example.apals.spotify_sdk_introduction.models.structs;

/**
 * Created by apals on 13/05/15.
 */
public class User {

    private String mName;
    private String mEmail;

    public User(String mName, String mEmail) {
        this.mName = mName;
        this.mEmail = mEmail;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getName() {
        return mName;
    }
}
