package ru.netology.mediaapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.mediaapp.model.AlbumData
import ru.netology.mediaapp.model.SongWithAlbum
import ru.netology.mediaapp.model.ViewDataModel
import ru.netology.mediaapp.model.ViewModelAlbumData
import ru.netology.mediaapp.repository.AlbumRepository
import ru.netology.mediaapp.repository.AlbumRepositoryImpl

class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val NO_ACTIVE_SONG = -1
    }

    private val repository: AlbumRepository = AlbumRepositoryImpl()
    private val _data = MutableLiveData(ViewDataModel())

    val data: LiveData<ViewDataModel>
        get() = _data

    private val _activeSong = MutableLiveData(SongWithAlbum(NO_ACTIVE_SONG, "", ""))

    val activeSong: LiveData<SongWithAlbum>
        get() = _activeSong

    fun loadData() {
        _data.postValue(ViewDataModel(loading = true))
        repository.getDataAsync(object : AlbumRepository.AlbumCallback<AlbumData> {
            override fun onSuccess(data: AlbumData) {
                val tracksList = data.tracks.map {
                    SongWithAlbum(it.id, it.file, data.title)
                }
                _data.postValue(
                    ViewDataModel(
                        data = ViewModelAlbumData(
                            id = data.id,
                            title = data.title,
                            artist = data.artist,
                            genre = data.genre,
                            published = data.published,
                            tracks = tracksList,
                        )
                    )
                )
            }

            override fun onError(throwable: Throwable) {
                _data.postValue(ViewDataModel(error = true))
            }
        })
    }

    fun setPlayingSong(song: SongWithAlbum) {
        _activeSong.postValue(song)
        val currentData = data.value?.data!!
        val songs = currentData.tracks.map {
            if (it.id == song.id) {
                SongWithAlbum(it.id, it.file, it.album, isPlaying = true)
            } else {
                SongWithAlbum(it.id, it.file, it.album, isPlaying = false)
            }
        }
        _data.postValue(ViewDataModel(
            data = ViewModelAlbumData(
                id = currentData.id,
                title = currentData.title,
                artist = currentData.artist,
                genre = currentData.genre,
                published = currentData.published,
                tracks = songs,
            )
        ))
    }

    fun stopSong() {
        _activeSong.postValue(SongWithAlbum(NO_ACTIVE_SONG, "", ""))
        val currentData = data.value?.data!!
        val songs = currentData.tracks.map {
            SongWithAlbum(it.id, it.file, it.album, isPlaying = false)
        }
        _data.postValue(ViewDataModel(
            data = ViewModelAlbumData(
                id = currentData.id,
                title = currentData.title,
                artist = currentData.artist,
                genre = currentData.genre,
                published = currentData.published,
                tracks = songs,
            )
        ))
    }
}