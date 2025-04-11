package com.myapp.musicplayerapp.ui.model

data class MusicUI(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val artworkUrl: String,
    val previewUrl: String,
    val isPlaying: Boolean,
    val isSelected: Boolean
)
