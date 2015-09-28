package com.example.epomeroy.sounddroid;

import android.media.AudioManager;
import android.media.MediaPlayer;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private List<Track> tracksList;
    private MediaPlayer mediaPlayer;
    private ImageView playerStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setPlayState();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playerStates.setImageResource(R.drawable.ic_play);
            }
        });

        Toolbar view = (Toolbar) findViewById(R.id.player_toolbar);
        final TextView selectedView = (TextView) findViewById(R.id.selected_title);
        final ImageView selectedImage = (ImageView) findViewById(R.id.selected_thumbnail);
        playerStates = (ImageView) findViewById(R.id.player_states);

        playerStates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPlayState();
            }
        });

        RecyclerView rv = (RecyclerView) findViewById(R.id.songs_list);
        rv.setLayoutManager(new LinearLayoutManager(this));

        tracksList = new ArrayList<>();
        final TracksAdapter tracksAdapter = new TracksAdapter(this, tracksList);
        tracksAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Track selectedTrack = tracksList.get(position);
                selectedView.setText(selectedTrack.getTitle());
                Picasso.with(MainActivity.this).load(selectedTrack.getAvatarURL()).into(selectedImage);

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }

                try {
                    mediaPlayer.setDataSource(selectedTrack.getStreamUrl() + "?client_id=" + SoundCloudService.CLIENT_ID);
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    Log.d(TAG, e.getMessage());
                }
            }
        });

        rv.setAdapter(tracksAdapter);

        SoundCloudService service = SoundCloud.getService();
        service.getMostRecentSongs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), new Callback<List<Track>>() {
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
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }

            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_view) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setPlayState() {
        if (this.mediaPlayer.isPlaying()) {
            this.playerStates.setImageResource(R.drawable.ic_play);
            this.mediaPlayer.pause();
        } else {
            this.playerStates.setImageResource(R.drawable.ic_pause);
            this.mediaPlayer.start();
        }
    }
}
