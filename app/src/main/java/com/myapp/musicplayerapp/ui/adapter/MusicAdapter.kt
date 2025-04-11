package com.myapp.musicplayerapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapp.musicplayerapp.databinding.ItemListContentBinding
import com.myapp.musicplayerapp.ui.model.MusicUI

class MusicAdapter(
    private val onItemClick: (MusicUI) -> Unit
) : ListAdapter<MusicUI, MusicAdapter.MusicViewHolder>(DIFF_CALLBACK) {



    inner class MusicViewHolder(private val binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(music: MusicUI) {
            binding.tvTitle.text = music.title
            binding.tvArtist.text = music.artist
            binding.tvAlbum.text = music.album

            Glide.with(binding.imgAlbumArt)
                .load(music.artworkUrl)
                .placeholder(android.R.color.darker_gray)
                .into(binding.imgAlbumArt)

            binding.imgWaveform.isVisible = music.isPlaying

            binding.root.setOnClickListener {
                onItemClick(music)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val binding = ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MusicUI>() {
            override fun areItemsTheSame(oldItem: MusicUI, newItem: MusicUI): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MusicUI, newItem: MusicUI): Boolean =
                oldItem == newItem
        }
    }
}
