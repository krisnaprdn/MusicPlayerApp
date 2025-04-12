package com.myapp.ui.mapper

import com.myapp.domain.model.MusicDomain
import com.myapp.ui.model.MusicUI

interface MusicMapper {
    fun toUI(domain: MusicDomain): MusicUI
    fun toDomain(ui: MusicUI): MusicDomain
    fun toUIList(domainList: List<MusicDomain>): List<MusicUI>
    fun toDomainList(uiList: List<MusicUI>): List<MusicDomain>
}