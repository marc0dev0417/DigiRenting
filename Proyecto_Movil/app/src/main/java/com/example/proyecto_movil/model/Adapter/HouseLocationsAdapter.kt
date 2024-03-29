package com.example.proyecto_movil.model.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_movil.CardDetailActivity
import com.example.proyecto_movil.R
import com.example.proyecto_movil.model.FavoriteDataSQL
import com.example.proyecto_movil.model.HouseLocation
import com.example.proyecto_movil.model.User
import com.example.proyecto_movil.sqltoken.ManagerToken
import com.flaviofaria.kenburnsview.KenBurnsView
import com.squareup.picasso.Picasso

class HouseLocationsAdapter(context: Context?, private var imageHouseList: MutableList<HouseLocation> ) :
    RecyclerView.Adapter<HouseLocationsAdapter.TravelLocationViewHolder>() {

    private var context = context
    private var liked = false

    private lateinit var databaseSQL: ManagerToken
    private var listSQL = mutableListOf<FavoriteDataSQL>()

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

        databaseSQL = ManagerToken(context)

        listSQL = databaseSQL.viewHouseFavorite()

        Log.d("sizeFavHome", listSQL.size.toString())

        Log.d("lastPosition", position.toString())

           for(favorite: FavoriteDataSQL in listSQL) {
                   if (imageHouseList[position].idHouse == favorite.houseId) {
                       liked = true
                       holder.imageViewLiked?.setImageResource(R.drawable.ic_baseline_favorite_24)
                   }
       }
        Picasso.get().load(imageHouseList[position].url).into(holder.kbvLocation)
        holder.textRegion?.text = imageHouseList[position].region
        holder.textAddress?.text = imageHouseList[position].address
        holder.textPrice?.text = imageHouseList[position].price.toString()

        holder.kbvLocation?.setOnClickListener {

            val intentInfoHouse = Intent(context, CardDetailActivity::class.java)

            intentInfoHouse.putExtra("intentUrl", imageHouseList[position].url)//Pass url to Activity
            intentInfoHouse.putExtra("intentUrlSecond", imageHouseList[position].url1) //Pass url1 to Activity
            intentInfoHouse.putExtra("intentRegion", imageHouseList[position].region) //Pass region to Activity
            intentInfoHouse.putExtra("intentAddress", imageHouseList[position].address)
            intentInfoHouse.putExtra("intentPrice", imageHouseList[position].price)
            intentInfoHouse.putExtra("intentSpace", imageHouseList[position].space)
            intentInfoHouse.putExtra("intentMail", imageHouseList[position].mail)

            context?.startActivity(intentInfoHouse)
        }

        holder.imageViewLiked?.setOnClickListener {
            if (!liked) {
                liked = true
                Toast.makeText(context, context?.getString(R.string.House_added_to_like) , Toast.LENGTH_SHORT).show()
                holder.imageViewLiked!!.setImageResource(R.drawable.ic_baseline_favorite_24)

                databaseSQL.addFavorite(
                    imageHouseList[position].idHouse!!,
                    imageHouseList[position].owner!!,
                    imageHouseList[position].url!!,
                    imageHouseList[position].region!!,
                    imageHouseList[position].price!!.toDouble(),
                    imageHouseList[position].userId!!
                )
            } else if (liked) {
                liked = false
                Toast.makeText(context,
                    context?.getString(R.string.House_not_added_to_like), Toast.LENGTH_SHORT).show()
                holder.imageViewLiked!!.setImageResource(R.drawable.ic_baseline_favorite_border_24)

                databaseSQL.deleteFavorite(imageHouseList[position].idHouse!!)


            }
        }
    }

    override fun getItemCount(): Int {
        return imageHouseList.size
    }
}