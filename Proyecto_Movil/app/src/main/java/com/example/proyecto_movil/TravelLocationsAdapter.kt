package com.example.proyecto_movil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flaviofaria.kenburnsview.KenBurnsView
import com.squareup.picasso.Picasso

class TravelLocationsAdapter( var travelLocations: List<TravelLocation> ) :
    RecyclerView.Adapter<TravelLocationsAdapter.TravelLocationViewHolder>() {

    class TravelLocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var kbvLocation : KenBurnsView = itemView.findViewById(R.id.kbvLocation)
        var textTitle : TextView = itemView.findViewById(R.id.textTitle)
        var textLocation : TextView = itemView.findViewById(R.id.textLocation)
        var textStarRating : TextView = itemView.findViewById(R.id.textStarRating)

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

    }

    override fun getItemCount(): Int {

        return travelLocations.size

    }

}