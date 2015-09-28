package com.example.epomeroy.sounddroid.soundcloud;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by epomeroy on 9/27/15.
 */
public interface SoundCloudService {
    String CLIENT_ID = "d3182ed39b8c3d5aedbbf9a887e1b8f2";

    @GET("/tracks?client_id=" + CLIENT_ID)
    public void searchSongs(@Query("q") String query, Callback<List<Track>> callback);
}
