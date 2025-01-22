package ru.netology.mediaapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.netology.mediaapp.databinding.SongBinding
import ru.netology.mediaapp.model.SongWithAlbum

class SongsAdapter(
    private val onInteractionListener: OnInteractionListener,
): ListAdapter<SongWithAlbum, SongViewHolder>(SongDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = SongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = getItem(position) ?: return
        holder.bind(song)
    }
}