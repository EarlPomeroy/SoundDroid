package com.example.epomeroy.sounddroid;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.epomeroy.sounddroid.soundcloud.Track;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by epomeroy on 9/27/15.
 */
public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.ViewHolder> {
    private final List<Track> trackList;
    private final Context context;
    private AdapterView.OnItemClickListener onItemClickListener;

    public TracksAdapter(Context context, List<Track> trackList) {
        this.context = context;
        this.trackList = trackList;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
        Picasso.with(context).load(track.getAvatarURL()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView titleTextView;
        private final ImageView imageView;

        ViewHolder(View v) {
            super(v);

            this.titleTextView = (TextView) v.findViewById(R.id.track_title);
            this.imageView = (ImageView) v.findViewById(R.id.track_thumbnail);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(null, v, getPosition(), 0);
            }
        }
    }
}
