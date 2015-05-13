package com.example.apals.spotify_sdk_introduction.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.apals.spotify_sdk_introduction.R;
import com.example.apals.spotify_sdk_introduction.models.structs.Track;

import java.util.ArrayList;

/**
 * Created by apals on 13/05/15.
 */
public class SearchResultsAdapter extends ArrayAdapter<Track> {

    private Activity mContext;

    public SearchResultsAdapter(Context context, int resource, ArrayList<Track> tracks) {
        super(context, resource, tracks);
        this.mContext = (Activity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mContext.getLayoutInflater().inflate(R.layout.search_results_listitem, null);
        TextView track = (TextView) v.findViewById(R.id.textview_track_name);
        TextView artist = (TextView) v.findViewById(R.id.textview_artist_name);
        track.setText(getItem(position).getName());
        String artistString = "";
        for(String s : getItem(position).getArtists())
            artistString += s + " : ";
        artist.setText(artistString);
        return v;
    }


}
