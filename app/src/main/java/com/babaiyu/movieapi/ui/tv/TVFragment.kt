package com.babaiyu.movieapi.ui.tv

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.babaiyu.movieapi.R
import com.babaiyu.movieapi.components.CardAdapter
import com.babaiyu.movieapi.models.CardViewModel
import com.babaiyu.movieapi.models.DataItems
import kotlinx.android.synthetic.main.fragment_movie.*
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.N)
class TVFragment : Fragment() {
    companion object {
        const val ID = "id"
        const val TYPE = "type"
    }

    private lateinit var rvTv: RecyclerView
    private lateinit var tvViewModel: CardViewModel
    private var stateData: ArrayList<DataItems> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_tv, container, false)

        rvTv = root.findViewById(R.id.recyclerTv)
        rvTv.setHasFixedSize(true)

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
        // Set Adapter
        rvTv.layoutManager = LinearLayoutManager(activity)
        val cardTvAdapter = CardAdapter(stateData)
        rvTv.adapter = cardTvAdapter

        // Model
        val type = "tv"
        tvViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(CardViewModel::class.java)
        tvViewModel.setData(type, setLanguage())
        tvViewModel.getData(type).observe(this, Observer { item ->
            if (item != null) {
                setData(item)
                cardTvAdapter.onItemClick = { data ->
                    val mBundle = Bundle()
                    mBundle.putInt(ID, data.id)
                    mBundle.putString(TYPE, type)
                    view!!.findNavController()
                        .navigate(R.id.action_navigation_tv_to_detailFragment, mBundle)
                }
                showLoading(false)
            }
        })
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

    private fun setData(item: ArrayList<DataItems>) {
        stateData.clear()
        stateData.addAll(item)
    }
}