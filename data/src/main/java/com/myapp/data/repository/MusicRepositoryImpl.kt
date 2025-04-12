package com.myapp.data.repository

import com.myapp.data.mapper.MusicResponseMapper
import com.myapp.data.service.SongService
import com.myapp.domain.MusicRepository
import com.myapp.domain.model.MusicDomain

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
