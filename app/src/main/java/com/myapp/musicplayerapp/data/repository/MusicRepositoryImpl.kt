package com.myapp.musicplayerapp.data.repository

import com.myapp.musicplayerapp.data.mapper.MusicResponseMapper
import com.myapp.musicplayerapp.data.service.SongService
import com.myapp.musicplayerapp.domain.model.MusicDomain
import com.myapp.musicplayerapp.domain.repository.MusicRepository

class MusicRepositoryImpl(
    private val api: SongService,
    private val mapper: MusicResponseMapper
) : MusicRepository {

    override suspend fun searchMusic(term: String): List<MusicDomain> {
        return api.searchSongs(term)
            .results.orEmpty()
            .map { mapper.map(it) }
    }
}
