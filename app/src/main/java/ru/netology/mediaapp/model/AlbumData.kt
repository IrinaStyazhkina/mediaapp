package ru.netology.mediaapp.model

data class AlbumData (
    val id: Int,
    val title: String,
    val subtitle: String,
    val artist: String,
    val published: String,
    val genre: String,
    val tracks: List<Song>
)

data class ViewModelAlbumData (
    val id: Int,
    val title: String,
    val artist: String,
    val published: String,
    val genre: String,
    val tracks: List<SongWithAlbum>
)