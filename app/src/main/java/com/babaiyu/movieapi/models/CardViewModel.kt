package com.babaiyu.movieapi.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class CardViewModel : ViewModel() {
    companion object {
        private const val API_KEY = "cf351551b4b72640578e20445eebffd6"
    }

    val listMovie = MutableLiveData<ArrayList<DataItems>>()
    val listTV = MutableLiveData<ArrayList<DataItems>>()

    internal fun setData(type: String, language: String) {
        val client = AsyncHttpClient()
        val listItems = ArrayList<DataItems>()
        val url =
            "https://api.themoviedb.org/3/discover/$type?api_key=$API_KEY&language=$language-ID"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")

                    for (i in 0 until list.length()) {
                        val data = list.getJSONObject(i)
                        val dataItems = DataItems()
                        val getPhoto = data.getString("poster_path")
                        val checkDescription = data.getString("overview")

                        dataItems.id = data.getInt("id")
                        dataItems.title = data.getString(
                            if (type == "movie") {
                                "title"
                            } else {
                                "name"
                            }
                        )
                        dataItems.release = data.getString(
                            if (type == "movie") {
                                "release_date"
                            } else {
                                "first_air_date"
                            }
                        )
                        dataItems.description = if (checkDescription.isEmpty()) {
                            if (language == "en") {
                                "No Description"
                            } else {
                                "Tidak ada Deskripsi"
                            }
                        } else {
                            checkDescription
                        }
                        dataItems.photo = "https://image.tmdb.org/t/p/w185/$getPhoto"

                        listItems.add(dataItems)
                    }
                    if (type == "movie") {
                        listMovie.postValue(listItems)
                    } else {
                        listTV.postValue(listItems)
                    }
                } catch (err: Exception) {
                    Log.d("Exception", err.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    internal fun getData(type: String): LiveData<ArrayList<DataItems>> {
        return if (type == "movie") {
            listMovie
        } else {
            listTV
        }
    }
}