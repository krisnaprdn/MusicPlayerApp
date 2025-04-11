package com.myapp.musicplayerapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.musicplayerapp.domain.repository.MusicRepository
import com.myapp.musicplayerapp.ui.mapper.MusicMapper
import com.myapp.musicplayerapp.ui.model.MusicUI
import kotlinx.coroutines.launch

class MusicViewModel(
    private val repository: MusicRepository,
    private val mapper: MusicMapper
) : ViewModel() {

    private val _musicList = MutableLiveData<List<MusicUI>>()
    val musicList: LiveData<List<MusicUI>> = _musicList

    fun search(term: String) {
        viewModelScope.launch {
            val domainList = repository.searchMusic(term)
            val uiList = mapper.toUIList(domainList)
            _musicList.value = uiList
        }
    }
}

