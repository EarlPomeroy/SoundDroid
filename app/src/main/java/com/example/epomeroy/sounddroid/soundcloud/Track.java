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
    @SerializedName("artwork_url")
    private String artworkUrl;

    public String getArtworkUrl() {
        return artworkUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public int getId() {
        return id;
    }

    public String getAvatarURL() {
        // if the artwork URL isn't null, replace large with tiny
        if (artworkUrl != null) {
            return artworkUrl.replace("large", "tiny");
        }

        // returning a null URL string
        return artworkUrl;
    }
}
