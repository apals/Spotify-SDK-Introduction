package com.example.apals.spotify_sdk_introduction.utils;

/**
 * Created by apals on 13/05/15.
 */
public class URLUtils {

    public static final String BASE_URL = "https://api.spotify.com/v1/search";

    public static String getSearchTrackURL(String query) {
        String URL = BASE_URL +
                "?q=" + query +
                "&market=SE" +
                "&type=track";
        return URL;
    }
}
