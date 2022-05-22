package com.example.proyecto_movil.model.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_movil.R

class FavoritesAdapter: RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    val titles = arrayOf("Titulo 1", "Titulo 2", "Titulo 3", "Titulo 4")
    val details = arrayOf("Muchos videos nuevos", "Kotlin!", "Más videos", "Gran cantidad de videos")
    var images = arrayOf(

        R.drawable.el_pana_claudio,
        R.drawable.google_logo,
        R.drawable.fb_logo,
        R.drawable.twitter_logo

    )

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init {

            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)

        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {

        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        viewHolder.itemTitle.text = titles[i]
        viewHolder.itemDetail.text = details[i]
        viewHolder.itemImage.setImageResource(images[i])

    }

    override fun getItemCount(): Int {

        return titles.size

    }

}