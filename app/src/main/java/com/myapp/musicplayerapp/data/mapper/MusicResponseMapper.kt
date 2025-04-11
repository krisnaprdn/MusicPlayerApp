package com.myapp.musicplayerapp.data.mapper

import com.myapp.musicplayerapp.data.model.Results
import com.myapp.musicplayerapp.domain.model.MusicDomain

interface MusicResponseMapper {
    fun map(response: Results): MusicDomain
}