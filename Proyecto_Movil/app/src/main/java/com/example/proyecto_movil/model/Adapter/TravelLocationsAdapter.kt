package com.example.proyecto_movil.model.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_movil.R
import com.example.proyecto_movil.TravelLocation
import com.flaviofaria.kenburnsview.KenBurnsView
import com.squareup.picasso.Picasso

class TravelLocationsAdapter(context: Context?, var travelLocations: List<TravelLocation> ) :
    RecyclerView.Adapter<TravelLocationsAdapter.TravelLocationViewHolder>() {

    private var context = context
    private var liked = false

    class TravelLocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var kbvLocation : KenBurnsView = itemView.findViewById(R.id.kbvLocation)
        var textTitle : TextView = itemView.findViewById(R.id.textTitle)
        var textLocation : TextView = itemView.findViewById(R.id.textLocation)
        var textStarRating : TextView = itemView.findViewById(R.id.textStarRating)
        var imageViewLiked: ImageView = itemView.findViewById(R.id.ivLiked_HomeFragment)

        fun setLocationData (travelLocation: TravelLocation) {

            Picasso.get().load(travelLocation.imageUrl).into(kbvLocation)
            textTitle.text = travelLocation.title
            textLocation.text = travelLocation.location
            textStarRating.text = travelLocation.starRating.toString()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelLocationViewHolder {

        return TravelLocationViewHolder(LayoutInflater.from(parent.context).inflate(

            R.layout.item_container_location,
            parent,
            false

        ))
    }

    override fun onBindViewHolder(holder: TravelLocationViewHolder, position: Int) {

        holder.setLocationData(travelLocations[position])

        holder.imageViewLiked.setOnClickListener {
            if (!liked) {
                liked = true
                Toast.makeText(context, "House added to like", Toast.LENGTH_SHORT).show()
                holder.imageViewLiked.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else if (liked) {
                liked = false
                Toast.makeText(context, "House not added to like", Toast.LENGTH_SHORT).show()
                holder.imageViewLiked.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }
    }

    override fun getItemCount(): Int {
        return travelLocations.size
    }
}