package com.myapp.musicplayerapp.domain.repository

import com.myapp.musicplayerapp.domain.model.MusicDomain

interface MusicRepository {
    suspend fun searchMusic(term: String): List<MusicDomain>
}