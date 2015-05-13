package com.example.apals.spotify_sdk_introduction.models.structs;

import java.util.List;

/**
 * Created by apals on 13/05/15.
 */
public class Track {

    private String mUri;
    private String mName;
    private List<String> artists;

    public Track(String mUri, String mName, List<String> artists) {
        this.mUri = mUri;
        this.mName = mName;
        this.artists = artists;
    }


    public String getName() {
        return mName;
    }


    public String getUri() {
        return mUri;
    }

    public List<String> getArtists() {
        return artists;
    }
}
