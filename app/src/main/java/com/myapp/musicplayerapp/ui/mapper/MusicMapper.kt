package com.myapp.musicplayerapp.ui.mapper

import com.myapp.musicplayerapp.domain.model.MusicDomain
import com.myapp.musicplayerapp.ui.model.MusicUI

interface MusicMapper {
    fun toUI(domain: MusicDomain): MusicUI
    fun toDomain(ui: MusicUI): MusicDomain
    fun toUIList(domainList: List<MusicDomain>): List<MusicUI>
    fun toDomainList(uiList: List<MusicUI>): List<MusicDomain>
}