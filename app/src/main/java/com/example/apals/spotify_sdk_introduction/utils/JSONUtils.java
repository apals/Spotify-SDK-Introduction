package com.example.apals.spotify_sdk_introduction.utils;

import com.example.apals.spotify_sdk_introduction.models.structs.Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apals on 13/05/15.
 */
public class JSONUtils {
    public static List<Track> getTracksWithArtistsFromJsonString(String json) throws JSONException {
        List<String> artistNames = new ArrayList<>();
        List<Track> tracks = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONObject("tracks").getJSONArray("items");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String uri = item.getString("uri");
            String name = item.getString("name");
            JSONArray artists = item.getJSONArray("artists");
            for(int j = 0; j < artists.length(); j++) {
                JSONObject artist = artists.getJSONObject(j);
                String artistName = artist.getString("name");
                artistNames.add(artistName);
            }
            tracks.add(new Track(uri, name, artistNames));
            artistNames = new ArrayList<>();
        }
        return tracks;
    }
}
