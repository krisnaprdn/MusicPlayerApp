package com.myapp.musicplayerapp.data.service

import com.myapp.musicplayerapp.data.model.SongResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SongService {
    @GET("search")
    suspend fun searchSongs(
        @Query("term") term: String,
        @Query("media") media: String = "music",
        @Query("limit") limit: Int = 10
    ): SongResponse
}