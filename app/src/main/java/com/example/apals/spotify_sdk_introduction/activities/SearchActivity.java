package com.example.apals.spotify_sdk_introduction.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.apals.spotify_sdk_introduction.R;
import com.example.apals.spotify_sdk_introduction.adapters.SearchResultsAdapter;
import com.example.apals.spotify_sdk_introduction.models.APIModel;
import com.example.apals.spotify_sdk_introduction.models.structs.LoggedInUser;
import com.example.apals.spotify_sdk_introduction.models.structs.Track;
import com.example.apals.spotify_sdk_introduction.models.structs.User;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apals on 13/05/15.
 */
public class SearchActivity extends Activity implements PlayerNotificationCallback, ConnectionStateCallback, AdapterView.OnItemClickListener {

    private Player mPlayer;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mListView = (ListView) findViewById(R.id.listview_results);
        mListView.setOnItemClickListener(this);

        Config playerConfig = new Config(this, LoggedInUser.authToken, getString(R.string.client_id));
        mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
            @Override
            public void onInitialized(Player player) {
                mPlayer.addConnectionStateCallback(SearchActivity.this);
                mPlayer.addPlayerNotificationCallback(SearchActivity.this);
                //mPlayer.play("spotify:track:2TpxZ7JUBn3uw46aR7qd6V");
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
            }
        });
    }

    public void search(View v) {
        String query = ((EditText) findViewById(R.id.edittext_search)).getText().toString();
        new AsyncTask<String, Void, List<Track>>() {
            @Override
            protected List<Track> doInBackground(String... strings) {
                List<Track> result = null;
                try {
                    result = APIModel.searchTracks(strings[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<Track> tracks) {
                super.onPostExecute(tracks);
                mListView.setAdapter(new SearchResultsAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, (ArrayList<Track>) tracks));
            }
        }.execute(query);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_about:
                showAboutDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAboutDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View v = getLayoutInflater().inflate(R.layout.dialog_user, null);
        new AsyncTask<Void, Void, User>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected User doInBackground(Void... voids) {
                User u = null;
                try {
                    u = APIModel.getCurrentUser();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return u;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                v.findViewById(R.id.progressbar_user).setVisibility(View.INVISIBLE);
                v.findViewById(R.id.linearlayout_user_info_container).setVisibility(View.VISIBLE);

                TextView usernameTextView = (TextView) v.findViewById(R.id.textview_username);
                usernameTextView.setText(user.getName());

                TextView emailTextView = (TextView) v.findViewById(R.id.textview_email);
                emailTextView.setText(user.getEmail());

            }
        }.execute();
        builder.setTitle(getString(R.string.user));
        builder.setView(v);
        builder.create().show();
    }

    @Override
    public void onLoggedIn() {

    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onLoginFailed(Throwable throwable) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {

    }

    @Override
    public void onPlaybackError(ErrorType errorType, String s) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mPlayer.play(((Track) mListView.getAdapter().getItem(i)).getUri());
    }
}
