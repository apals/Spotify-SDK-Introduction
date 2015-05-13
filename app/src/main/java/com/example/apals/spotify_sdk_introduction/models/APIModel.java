package com.example.apals.spotify_sdk_introduction.models;

import com.example.apals.spotify_sdk_introduction.models.structs.User;
import com.example.apals.spotify_sdk_introduction.utils.HttpUtils;
import com.example.apals.spotify_sdk_introduction.utils.JSONUtils;
import com.example.apals.spotify_sdk_introduction.models.structs.Track;
import com.example.apals.spotify_sdk_introduction.utils.URLUtils;

import org.apache.http.HttpResponse;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

/**
 * Created by apals on 13/05/15.
 */
public class APIModel {

    public static List<Track> searchTracks(String query) throws IOException, JSONException {
        query = query.replaceAll(" ", "%20");
        HttpResponse response = HttpUtils.doGet(URLUtils.getSearchTrackURL(query));

        String json = convertStreamToString(response.getEntity().getContent());
        List<Track> tracks = JSONUtils.getTracksWithArtistsFromJsonString(json);

        return tracks;
    }

    public static User getCurrentUser() throws IOException, JSONException {
        HttpResponse response = HttpUtils.doGet(URLUtils.getCurrentUserURL());
        String json = convertStreamToString(response.getEntity().getContent());
        User user = JSONUtils.getUserFromJson(json);
        return user;
    }

    public static String convertStreamToString(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 1024);
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                inputStream.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }
}
