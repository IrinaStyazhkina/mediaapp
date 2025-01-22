package ru.netology.mediaapp.model

data class ViewDataModel(
    val loading: Boolean = false,
    val error: Boolean = false,
    val data: ViewModelAlbumData? = null,
)