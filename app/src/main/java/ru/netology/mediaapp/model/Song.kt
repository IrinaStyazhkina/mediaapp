package ru.netology.mediaapp.model

data class Song(val id: Int, val file: String)

data class SongWithAlbum(val id: Int, val file: String, val album: String, val isPlaying: Boolean = false)
