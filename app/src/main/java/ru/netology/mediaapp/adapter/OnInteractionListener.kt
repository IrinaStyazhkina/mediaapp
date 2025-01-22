package ru.netology.mediaapp.adapter

import ru.netology.mediaapp.model.SongWithAlbum

interface OnInteractionListener {
    fun onPlay(song: SongWithAlbum){}
    fun onPause(song: SongWithAlbum){}
}