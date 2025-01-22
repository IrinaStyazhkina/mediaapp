package ru.netology.mediaapp.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.netology.mediaapp.databinding.SongBinding
import ru.netology.mediaapp.model.SongWithAlbum

class SongViewHolder(
    private val binding: SongBinding,
    private val onInteractionListener: OnInteractionListener,
    ): RecyclerView.ViewHolder(binding.root) {

    fun bind(song: SongWithAlbum) {
            binding.apply {
                tvSongName.text = song.id.toString()
                tvAlbum.text = song.album
                play.setOnClickListener {
                    onInteractionListener.onPlay(song)
                }
                stop.setOnClickListener {
                    onInteractionListener.onPause(song)
                }
                if (song.isPlaying) {
                    stop.isVisible = true
                    play.isVisible = false
                } else {
                    stop.isVisible = false
                    play.isVisible = true
                }
            }
        }
}