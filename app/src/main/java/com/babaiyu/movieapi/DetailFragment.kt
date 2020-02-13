package com.babaiyu.movieapi


import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.babaiyu.movieapi.ui.movie.MovieFragment
import com.babaiyu.movieapi.ui.tv.TVFragment

/**
 * A simple [Fragment] subclass.
 */
@RequiresApi(Build.VERSION_CODES.N)
class DetailFragment : Fragment() {

    private lateinit var type: String
    private lateinit var id: String

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

        return root
    }
}
