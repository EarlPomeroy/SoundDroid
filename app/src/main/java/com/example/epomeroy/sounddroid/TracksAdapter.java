package com.example.epomeroy.sounddroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.epomeroy.sounddroid.soundcloud.Track;

import java.util.List;

/**
 * Created by epomeroy on 9/27/15.
 */
public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.ViewHolder> {
    private final List<Track> trackList;

    public TracksAdapter(List<Track> trackList) {
        this.trackList = trackList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Track track = trackList.get(position);
        holder.titleTextView.setText(track.getTitle());
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private final TextView titleTextView;

        ViewHolder(View v) {
            super(v);

            this.titleTextView = (TextView) v.findViewById(R.id.track_title);
        }
    }
}
