package com.myapp.data.mapper

import com.myapp.data.model.Results
import com.myapp.domain.model.MusicDomain

interface MusicResponseMapper {
    fun map(response: Results): MusicDomain
}