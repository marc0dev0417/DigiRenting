package com.example.proyecto_movil.model.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_movil.R
import com.example.proyecto_movil.model.ModelRemoveHouse
import com.squareup.picasso.Picasso

class AdapterRemoveHouse(context: Context? = null, listImage: MutableList<ModelRemoveHouse>) : RecyclerView.Adapter<AdapterRemoveHouse.ImageHolder>() {
    private var context: Context = context!!
     private var listRemoveHouse: MutableList<ModelRemoveHouse> = listImage

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView? = null
        var textPrice: TextView? = null
        var textRegion: TextView? = null

        var buttonRemove: ImageView? = null

        init {
        imageView = itemView.findViewById(R.id.image_house_url)
        textPrice = itemView.findViewById(R.id.item_price_remove)
        textRegion = itemView.findViewById(R.id.item_region_remove)

        buttonRemove = itemView.findViewById(R.id.button_remove)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.list_house_remove_card, parent, false)
        return ImageHolder(view)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {

        Picasso.get().load(listRemoveHouse[position].url).resize(400, 400).into(holder.imageView)
        holder.textPrice?.text = listRemoveHouse[position].price.toString()
        holder.textRegion?.text = listRemoveHouse[position].region

        holder.buttonRemove?.setOnClickListener {
            Toast.makeText(context, "hello i am position $position", Toast.LENGTH_SHORT).show()

            var newPosition = position

            listRemoveHouse.removeAt(newPosition)
             notifyItemRemoved(newPosition)

            notifyItemRangeChanged(position, listRemoveHouse.size)

        }
    }

    override fun getItemCount(): Int {
        return listRemoveHouse.size
    }

}