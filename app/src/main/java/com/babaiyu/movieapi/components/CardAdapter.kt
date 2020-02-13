package com.babaiyu.movieapi.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.babaiyu.movieapi.R
import com.babaiyu.movieapi.models.DataItems
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_item.view.*

class CardAdapter(private val mData: ArrayList<DataItems>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    var onItemClick: ((DataItems) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CardViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: DataItems) {
            with(itemView) {
                item_title.text = item.title
                item_release.text = item.release
                item_description.text = item.description
                Glide.with(itemView.context)
                    .load(item.photo)
                    .into(item_img)
            }
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(mData[adapterPosition])
            }
        }
    }
}
