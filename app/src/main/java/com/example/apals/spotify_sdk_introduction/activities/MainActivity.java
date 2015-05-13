package com.example.apals.spotify_sdk_introduction.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.apals.spotify_sdk_introduction.R;
import com.example.apals.spotify_sdk_introduction.models.structs.LoggedInUser;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Spotify;


public class MainActivity extends Activity {

    private static final int REQUEST_CODE = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(getString(R.string.client_id),
                AuthenticationResponse.Type.TOKEN,
                getString(R.string.spotify_callback));
        builder.setScopes(new String[]{"user-read-private", "streaming", "user-read-email"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            Intent i = new Intent(this, SearchActivity.class);
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN)
                LoggedInUser.authToken = response.getAccessToken();
            startActivity(i);

        }
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }



}
