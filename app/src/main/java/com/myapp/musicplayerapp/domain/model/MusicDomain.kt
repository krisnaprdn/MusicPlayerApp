package com.myapp.musicplayerapp.domain.model

data class MusicDomain(
    val id: Long,
    val title: String,
    val albumName: String,
    val artist: String,
    val previewUrl: String,
    val artworkUrl: String
)