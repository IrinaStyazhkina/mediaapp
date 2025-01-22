package ru.netology.mediaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.ContentView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import okhttp3.internal.notifyAll
import ru.netology.mediaapp.adapter.OnInteractionListener
import ru.netology.mediaapp.adapter.SongsAdapter
import ru.netology.mediaapp.model.Song
import ru.netology.mediaapp.model.SongWithAlbum
import ru.netology.mediaapp.observer.MediaLifecycleObserver

import ru.netology.mediaapp.viewModel.AlbumViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: AlbumViewModel by viewModels()
    private val observer = MediaLifecycleObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(observer)

        setContentView(R.layout.activity_main)

        val progress = findViewById<ProgressBar>(R.id.progress)
        val errorText = findViewById<TextView>(R.id.errorText)
        val albumName = findViewById<TextView>(R.id.tvAlbumName)
        val artistName = findViewById<TextView>(R.id.tvPresenterName)
        val years = findViewById<TextView>(R.id.tvYear)
        val genre = findViewById<TextView>(R.id.tvGenre)
        val list = findViewById<RecyclerView>(R.id.list)
        viewModel.loadData()

        val adapter = SongsAdapter(object : OnInteractionListener {
            override fun onPlay(song: SongWithAlbum) {
                viewModel.setPlayingSong(song)
            }
            override fun onPause(song: SongWithAlbum) {
                viewModel.stopSong()
            }
        })

        list.adapter = adapter

        viewModel.data.observe(this) { state ->
            progress.isVisible = state.loading
            errorText.isVisible = state.error

            if (state.data !== null) {
                albumName.text = state.data.title
                artistName.text = state.data.artist
                years.text = state.data.published
                genre.text = state.data.genre
                adapter.submitList(state.data.tracks.map {
                    SongWithAlbum(it.id, it.file, state.data.title, it.isPlaying)
                })
            }
        }

        viewModel.activeSong.observe(this) { activeSong ->
            if (activeSong.id > 0) {
                observer.apply {
                    mediaPlayer?.reset()
                    mediaPlayer?.setDataSource(
                        "https://raw.githubusercontent.com/netology-code/andad-homeworks/master/09_multimedia/data/${activeSong.file}"
                    )
                    mediaPlayer?.setOnCompletionListener {
                        val songs = adapter.currentList
                        val indexOfCurrentSong = songs.indexOf(activeSong)
                        if (indexOfCurrentSong == songs.size -1) {
                            viewModel.setPlayingSong(songs[0])
                        } else {
                            viewModel.setPlayingSong(songs[indexOfCurrentSong + 1])
                        }
                    }
                }.play()
            } else {
                observer.apply {
                    mediaPlayer?.pause()
                }
            }
        }
    }
}