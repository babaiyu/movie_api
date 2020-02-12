package com.babaiyu.movieapi.ui.movie

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.babaiyu.movieapi.R
import com.babaiyu.movieapi.components.CardAdapter
import com.babaiyu.movieapi.models.CardViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
class MovieFragment : Fragment() {
    private lateinit var rvMovies: RecyclerView
    private lateinit var adapter: CardAdapter
    private lateinit var movieViewModel: CardViewModel
    private lateinit var getLanguage: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_movie, container, false)

        setLanguage()
        Log.d("LANGUAGE", getLanguage)

        adapter = CardAdapter()
        adapter.notifyDataSetChanged()

        rvMovies = root.findViewById(R.id.recyclerView)
        rvMovies.setHasFixedSize(true)

        rvMovies.layoutManager = LinearLayoutManager(activity)
        rvMovies.adapter = adapter


        componentDidMount()

        return root
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun componentDidMount() {
        movieViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(CardViewModel::class.java)
        movieViewModel.setData("movie", getLanguage)
        movieViewModel.getData().observe(this, Observer { item ->
            if (item != null) {
                adapter.setData(item)
                showLoading(false)
            }
        })
    }

    private fun setLanguage() {
        val langNow = resources.configuration.locales[0].toString().substring(3, 5).toLowerCase(Locale.ROOT)
        getLanguage = if (langNow === "id") {
            "id"
        } else {
            "en"
        }
    }
}