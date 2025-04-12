package com.myapp.ui.mapper

import com.myapp.domain.model.MusicDomain
import com.myapp.ui.model.MusicUI

class MusicMapperImpl : MusicMapper {
    override fun toUI(domain: MusicDomain): MusicUI {
        return MusicUI(
            id = domain.id,
            title = domain.title,
            artist = domain.artist,
            album = domain.albumName,
            artworkUrl = domain.artworkUrl,
            previewUrl = domain.previewUrl,
            isPlaying = false,
            isSelected = false
        )
    }

    override fun toDomain(ui: MusicUI): MusicDomain {
        return MusicDomain(
            id = ui.id,
            title = ui.title,
            artist = ui.artist,
            albumName = ui.album,
            artworkUrl = ui.artworkUrl,
            previewUrl = ui.previewUrl
        )
    }

    override fun toUIList(domainList: List<MusicDomain>): List<MusicUI> {
        return domainList.map { toUI(it) }
    }

    override fun toDomainList(uiList: List<MusicUI>): List<MusicDomain> {
        return uiList.map { toDomain(it) }
    }
}