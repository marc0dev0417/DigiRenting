package com.example.proyecto_movil.model.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.proyecto_movil.R
import com.example.proyecto_movil.model.ModelRemoveHouse
import com.example.proyecto_movil.sqltoken.ManagerToken
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class AdapterRemoveHouse(context: Context? = null, listImage: MutableList<ModelRemoveHouse>) : RecyclerView.Adapter<AdapterRemoveHouse.ImageHolder>() {
    private var context: Context = context!!
    private var listRemoveHouse: MutableList<ModelRemoveHouse> = listImage

    private val gson = Gson()
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var databaseSQL: ManagerToken

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

        databaseSQL = ManagerToken(context)

       val userProfile = databaseSQL.viewUserWithToken()[0]

        Picasso.get().load(listRemoveHouse[position].url).resize(400, 400).into(holder.imageView)
        holder.textPrice?.text = listRemoveHouse[position].price.toString() + " $"
        holder.textRegion?.text = listRemoveHouse[position].region

        holder.buttonRemove?.setOnClickListener {
            val positionHouseFavorite = listRemoveHouse[position].idHouse
            val stringRequest = object : StringRequest(
                Method.DELETE, "http://192.168.1.142:8080/users/delete/house?idUser=${userProfile.idUser}&idHouse=${listRemoveHouse[position].idHouse}",
                {
                    var newPosition = position

                    listRemoveHouse.removeAt(newPosition)
                    notifyItemRemoved(newPosition)

                    notifyItemRangeChanged(position, listRemoveHouse.size)

                    databaseSQL.deleteFavorite(positionHouseFavorite!!)
                }, {
                    Toast.makeText(context, context.getString(R.string.Fallo_en_tal_cosa) + "$it", Toast.LENGTH_SHORT).show()
                }){
                override fun getBodyContentType(): String {
                    return "application/json"
                }

                override fun getHeaders(): MutableMap<String, String> {
                    val accessTokenApi: HashMap<String, String> = HashMap()

                    accessTokenApi["Authorization"] = "Bearer ${userProfile.token}"

                    return accessTokenApi
                }
            }
            mRequestQueue = Volley.newRequestQueue(context)
            mRequestQueue.add(stringRequest)
        }
    }

    override fun getItemCount(): Int {
        return listRemoveHouse.size
    }

}