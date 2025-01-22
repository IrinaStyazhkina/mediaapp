package ru.netology.mediaapp.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.netology.mediaapp.model.Song
import ru.netology.mediaapp.model.SongWithAlbum

class SongDiffCallback: DiffUtil.ItemCallback<SongWithAlbum>() {
    override fun areItemsTheSame(oldItem: SongWithAlbum, newItem: SongWithAlbum): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SongWithAlbum, newItem: SongWithAlbum): Boolean {
        return oldItem == newItem
    }
}