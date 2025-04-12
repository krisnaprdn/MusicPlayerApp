package com.myapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.domain.MusicRepository
import com.myapp.ui.mapper.MusicMapper
import com.myapp.ui.model.MusicUI
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

