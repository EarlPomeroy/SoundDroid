package com.example.epomeroy.sounddroid.soundcloud;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by epomeroy on 9/27/15.
 */
public class Track {
    @SerializedName("title")
    private String title;
    @SerializedName("stream_url")
    private String streamUrl;
    @SerializedName("id")
    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
