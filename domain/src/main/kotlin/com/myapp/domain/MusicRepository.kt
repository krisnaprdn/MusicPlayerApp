package com.myapp.domain

import com.myapp.domain.model.MusicDomain

interface MusicRepository {
    suspend fun searchMusic(term: String): List<MusicDomain>
}