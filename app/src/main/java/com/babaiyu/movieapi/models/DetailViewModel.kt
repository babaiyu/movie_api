package com.babaiyu.movieapi.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Error

class DetailViewModel : ViewModel() {
    companion object {
        private const val API_KEY = "cf351551b4b72640578e20445eebffd6"
    }

    val detailView = MutableLiveData<DetailItems>()

    internal fun setData(type: String, id: String, language: String) {
        val client = AsyncHttpClient()
        var listItems: DetailItems
        val url =
            "https://api.themoviedb.org/3/$type/$id?api_key=$API_KEY&language=$language-ID"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val response = JSONObject(result)
                    val detailItems = DetailItems()
                    val getPhoto = response.getString("poster_path")
                    val getDuration = if (type == "movie") {
                        response.getInt("runtime")
                    } else {
                        response.getJSONArray("episode_run_time").getInt(0)
                    }
                    detailItems.id = response.getInt("id")
                    detailItems.title = response.getString(
                        if (type == "movie") {
                            "title"
                        } else {
                            "name"
                        }
                    )
                    detailItems.release = response.getString(if (type == "movie") {
                        "release_date"
                    } else {
                        "first_air_date"
                    })
                    detailItems.description = response.getString("overview")
                    detailItems.photo = "https://image.tmdb.org/t/p/original/$getPhoto"
                    detailItems.language = response.getString("original_language")
                    detailItems.duration = getDuration
                    detailItems.score = response.getInt("vote_average")
                    listItems = detailItems
                    detailView.postValue(listItems)
                } catch (err: Error) {
                    Log.d("ERROR DETAIL ITEM", err.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("ON FAILURE DETAIL ITEM", error.message.toString())
            }
        })
    }

    internal fun getData(): LiveData<DetailItems> {
        return detailView
    }
}