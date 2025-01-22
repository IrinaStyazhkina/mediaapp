package ru.netology.mediaapp.repository

import ru.netology.mediaapp.model.AlbumData

interface AlbumRepository {
    fun getDataAsync(callback: AlbumCallback<AlbumData>)

    interface AlbumCallback<T> {
        fun onSuccess(data: T)
        fun onError(throwable: Throwable)
    }
}