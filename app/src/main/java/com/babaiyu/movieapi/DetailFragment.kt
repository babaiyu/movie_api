package com.babaiyu.movieapi

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.babaiyu.movieapi.models.DetailViewModel
import com.babaiyu.movieapi.ui.movie.MovieFragment
import com.babaiyu.movieapi.ui.tv.TVFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
@RequiresApi(Build.VERSION_CODES.N)
class DetailFragment : Fragment() {

    private lateinit var type: String
    private lateinit var id: String
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_detail, container, false)

        val textID = root.findViewById<TextView>(R.id.detail_id)
        val textType = root.findViewById<TextView>(R.id.detail_type)

        type = arguments?.getString(MovieFragment.TYPE)
            ?: arguments?.getString(TVFragment.TYPE).toString()
        id = arguments?.getInt(MovieFragment.ID)?.toString()
            ?: arguments?.getString(TVFragment.ID).toString()
        textType.text = type
        textID.text = id

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        detailViewModel.setData(type, id, setLanguage())
        detailViewModel.getData().observe(this, Observer { item ->
            if (item != null) {
                Glide.with(detailImage)
                    .load(item.photo)
                    .into(detailImage)
                Log.d("DATA DETAIL RESULT", item.description.toString())
            }
        })

        return root
    }

    private fun setLanguage(): String {
        val langNow =
            resources.configuration.locales[0].toString().substring(3, 5).toLowerCase(Locale.ROOT)
        Log.d("LANGUAGE LIST", resources.configuration.locales.toString())
        Log.d("LANGUAGE USED", langNow)
        return if (langNow == "us") {
            "en"
        } else {
            "id"
        }
    }
}
