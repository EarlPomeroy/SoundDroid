package com.example.epomeroy.sounddroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.epomeroy.sounddroid.soundcloud.SoundCloud;
import com.example.epomeroy.sounddroid.soundcloud.SoundCloudService;
import com.example.epomeroy.sounddroid.soundcloud.Track;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TracksAdapter tracksAdapter;
    private List<Track> tracksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar view = (Toolbar) findViewById(R.id.player_toolbar);
        final TextView selectedView = (TextView) findViewById(R.id.selected_title);
        final ImageView selectedImage = (ImageView) findViewById(R.id.selected_thumbnail);

        RecyclerView rv = (RecyclerView) findViewById(R.id.songs_list);
        rv.setLayoutManager(new LinearLayoutManager(this));

        tracksList = new ArrayList<>();
        tracksAdapter = new TracksAdapter(this, tracksList);
        tracksAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Track track = tracksList.get(position);
                selectedView.setText(track.getTitle());
                Picasso.with(MainActivity.this).load(track.getAvatarURL()).into(selectedImage);
            }
        });

        rv.setAdapter(tracksAdapter);

        SoundCloudService service = SoundCloud.getService();
        service.searchSongs("dark horse", new Callback<List<Track>>() {
            @Override
            public void success(List<Track> tracks, Response response) {
                tracksList.clear();
                tracksList.addAll(tracks);
                tracksAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Error is " + error);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
