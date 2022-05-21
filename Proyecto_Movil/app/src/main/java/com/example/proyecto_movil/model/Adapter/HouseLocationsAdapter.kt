package com.example.proyecto_movil.model.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_movil.R
import com.example.proyecto_movil.model.HouseLocation
import com.example.proyecto_movil.model.User
import com.flaviofaria.kenburnsview.KenBurnsView
import com.squareup.picasso.Picasso

class HouseLocationsAdapter(context: Context?, private var imageHouseList: MutableList<HouseLocation> ) :
    RecyclerView.Adapter<HouseLocationsAdapter.TravelLocationViewHolder>() {

    private var context = context
    private var liked = false

    class TravelLocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var kbvLocation: KenBurnsView? = null

        var textRegion: TextView? = null
        var textAddress : TextView? = null
        var textPrice : TextView? = null
        var imageViewLiked: ImageView? = null

        init {
             kbvLocation  = itemView.findViewById(R.id.kbvLocation)
             textRegion = itemView.findViewById(R.id.textRegion)
             textAddress = itemView.findViewById(R.id.textAddress)
            textPrice  = itemView.findViewById(R.id.textPrice)
             imageViewLiked = itemView.findViewById(R.id.ivLiked_HomeFragment)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelLocationViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_container_location, parent, false)

        return TravelLocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: TravelLocationViewHolder, position: Int) {

        Picasso.get().load(imageHouseList[position].url).into(holder.kbvLocation)
        holder.textRegion?.text = imageHouseList[position].region
        holder.textAddress?.text = imageHouseList[position].address
        holder.textPrice?.text = imageHouseList[position].price.toString()

        holder.kbvLocation?.setOnClickListener {
            Toast.makeText(context, "hola funciono $position", Toast.LENGTH_SHORT).show()
            Log.d("xD1", imageHouseList[position].url1.toString()+" "+imageHouseList[position].url2+ " owner "+imageHouseList[position].owner)
        }

        holder.imageViewLiked?.setOnClickListener {
            if (!liked) {
                liked = true
                Toast.makeText(context, "House added to like", Toast.LENGTH_SHORT).show()
                holder.imageViewLiked!!.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else if (liked) {
                liked = false
                Toast.makeText(context, "House not added to like", Toast.LENGTH_SHORT).show()
                holder.imageViewLiked!!.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }
    }

    override fun getItemCount(): Int {
        return imageHouseList.size
    }
}