package com.example.proyecto_movil.model.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_movil.R
import com.example.proyecto_movil.model.FavoriteDataSQL
import com.example.proyecto_movil.model.ModelFavoriteHouse
import com.squareup.picasso.Picasso

class FavoritesAdapter(context: Context? = null, listFavorite: MutableList<FavoriteDataSQL>? = null): RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private var context = context!!
    private var listFavoriteHouse = listFavorite

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView = itemView.findViewById(R.id.item_image)
        var itemRegion: TextView = itemView.findViewById(R.id.item_region)
        var itemPrice: TextView = itemView.findViewById(R.id.item_price)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {

        val v = LayoutInflater.from(context).inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        Picasso.get().load(listFavoriteHouse!![i].url).into(viewHolder.itemImage)
        viewHolder.itemRegion.text = listFavoriteHouse!![i].region
        viewHolder.itemPrice.text = listFavoriteHouse!![i].price.toString() + " $"


    }

    override fun getItemCount(): Int {

        return listFavoriteHouse?.size!!

    }

}