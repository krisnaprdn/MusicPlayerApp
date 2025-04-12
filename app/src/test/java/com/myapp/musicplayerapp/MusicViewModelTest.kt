package com.myapp.musicplayerapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.myapp.musicplayerapp.domain.model.MusicDomain
import com.myapp.musicplayerapp.domain.repository.MusicRepository
import com.myapp.musicplayerapp.ui.mapper.MusicMapper
import com.myapp.musicplayerapp.ui.model.MusicUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MusicViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var repository: MusicRepository

    @Mock
    private lateinit var mapper: MusicMapper

    @Mock
    private lateinit var observer: Observer<List<MusicUI>>

    private lateinit var viewModel: MusicViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MusicViewModel(repository, mapper)
        viewModel.musicList.observeForever(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search should fetch domain list and update LiveData with mapped UI list`() = runTest {
        // Given
        val term = "Taylor Swift"
        val domainList = listOf(MusicDomain(1, "Title", "Artist", "Album", "url", "url"))
        val uiList = listOf(MusicUI(1, "Title", "Artist", "Album", "url", "url", false, false))

        `when`(repository.searchMusic(term)).thenReturn(domainList)
        `when`(mapper.toUIList(domainList)).thenReturn(uiList)

        // When
        viewModel.search(term)
        testDispatcher.scheduler.advanceUntilIdle() // ensure coroutine completes

        // Then
        verify(observer).onChanged(uiList)
    }
}
