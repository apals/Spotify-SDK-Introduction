package com.example.apals.spotify_sdk_introduction.utils;

import com.example.apals.spotify_sdk_introduction.models.structs.LoggedInUser;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;

import java.io.IOException;

/**
 * Created by apals on 13/05/15.
 */
public class HttpUtils {

    private static HttpClient httpClient;

    static {
        httpClient = new DefaultHttpClient();
    }

    public static BasicHttpResponse doGet(String url) {
        BasicHttpResponse response = null;
        try {
            HttpGet request = new HttpGet(url);
            request.addHeader("Authorization", "Bearer " + LoggedInUser.authToken);
            for (Header h : request.getAllHeaders())
                System.out.println("HEADER: " + h.toString());
            System.out.println(url);
            //request.setURI(new URI(url));

            response = (BasicHttpResponse) httpClient.execute(request);
        } /*catch (URISyntaxException e) {
            e.printStackTrace();
        }*/ catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
