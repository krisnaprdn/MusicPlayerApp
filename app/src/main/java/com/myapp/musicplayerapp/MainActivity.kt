package com.myapp.musicplayerapp

import android.media.MediaPlayer
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.musicplayerapp.databinding.ActivityMainBinding
import com.myapp.musicplayerapp.ui.adapter.MusicAdapter
import com.myapp.musicplayerapp.ui.viewmodel.MusicViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MusicViewModel by viewModel()
    private val adapter: MusicAdapter by lazy {
        MusicAdapter { song ->
            val index = adapter.currentList.indexOf(song)
            if (index != -1) {
                playPreview(index, song.previewUrl)
            }
        }
    }
    private var mediaPlayer: MediaPlayer? = null
    private var currentIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        observeLiveData()
    }

    private fun setupUI() = with(binding) {
        rvContentMedia.layoutManager = LinearLayoutManager(this@MainActivity)
        rvContentMedia.adapter = adapter

        etSearchArtist.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.search(v.text.toString())
                hideKeyboard()
                true
            } else false
        }

        previous.setOnClickListener { playAdjacent(-1) }
        next.setOnClickListener { playAdjacent(1) }

        playPause.setOnClickListener {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.pause()
                    playPause.setImageResource(R.drawable.ic_play)
                } else {
                    it.start()
                    playPause.setImageResource(R.drawable.ic_pause)
                }
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })
    }

    private fun playAdjacent(direction: Int) {
        val list = adapter.currentList
        val targetIndex = currentIndex + direction
        if (targetIndex in list.indices) {
            playPreview(targetIndex, list[targetIndex].previewUrl)
        }
    }

    private fun playPreview(index: Int, url: String) {
        currentIndex = index
        mediaPlayer?.release()

        adapter.submitList(adapter.currentList.mapIndexed { i, item ->
            item.copy(isPlaying = i == index, isSelected = i == index)
        })

        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            setOnPreparedListener {
                binding.seekBar.max = duration
                start()
                binding.playPause.setImageResource(R.drawable.ic_pause)
                binding.llPlayerControl.isVisible = true
                updateSeekBar()
            }
            setOnCompletionListener {
                binding.playPause.setImageResource(R.drawable.ic_play)
                binding.llPlayerControl.isVisible = false
                adapter.submitList(adapter.currentList.map { it.copy(isPlaying = false, isSelected = false) })
            }
            prepareAsync()
        }
    }

    private fun updateSeekBar() {
        lifecycleScope.launch {
            while (mediaPlayer?.isPlaying == true) {
                binding.seekBar.progress = mediaPlayer?.currentPosition ?: 0
                delay(500)
            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearchArtist.windowToken, 0)
    }

    private fun observeLiveData() {
        viewModel.musicList.observe(this) { adapter.submitList(it) }
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        binding.rvContentMedia.adapter = null
        super.onDestroy()
    }
}
