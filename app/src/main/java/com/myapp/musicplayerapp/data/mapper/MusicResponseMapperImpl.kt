package com.myapp.musicplayerapp.data.mapper

import com.myapp.musicplayerapp.data.model.Results
import com.myapp.musicplayerapp.domain.model.MusicDomain


class MusicResponseMapperImpl : MusicResponseMapper {
    override fun map(response: Results): MusicDomain {
        return MusicDomain(
            id = response.trackId ?: 0L,
            title = response.trackName.orEmpty(),
            artist = response.artistName.orEmpty(),
            albumName = response.collectionName.orEmpty(),
            previewUrl = response.previewUrl.orEmpty(),
            artworkUrl = response.artworkUrl100.orEmpty()
        )
    }
}