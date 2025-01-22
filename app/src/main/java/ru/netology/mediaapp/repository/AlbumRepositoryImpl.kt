package ru.netology.mediaapp.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import ru.netology.mediaapp.model.AlbumData
import java.io.IOException
import java.util.concurrent.TimeUnit

class AlbumRepositoryImpl: AlbumRepository {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()

    companion object {
        private const val URL = "https://github.com/netology-code/andad-homeworks/raw/master/09_multimedia/data/album.json"
        private val typeToken = object : TypeToken<AlbumData>() {}
    }

    override fun getDataAsync(callback: AlbumRepository.AlbumCallback<AlbumData>) {
        val request: Request = Request.Builder()
            .url(URL)
            .build()

        return client.newCall(request)
            .enqueue(
                object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onError(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseText = response.body?.string()
                        if (responseText == null) {
                            callback.onError(RuntimeException("body is null"))
                            return
                        }
                        try {
                            val data = gson.fromJson(responseText, typeToken)
                            callback.onSuccess(gson.fromJson(responseText, typeToken))
                        } catch (e: Exception) {
                            callback.onError(e)
                        }
                    }
                }
            )
    }
}