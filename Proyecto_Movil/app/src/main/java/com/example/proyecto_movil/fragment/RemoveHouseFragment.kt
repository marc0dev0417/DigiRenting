package com.example.proyecto_movil.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.proyecto_movil.R
import com.example.proyecto_movil.model.Adapter.AdapterRemoveHouse
import com.example.proyecto_movil.model.ModelRemoveHouse
import com.example.proyecto_movil.model.User
import com.example.proyecto_movil.model.UserDataSQL
import com.example.proyecto_movil.sqltoken.ManagerToken
import com.google.gson.Gson

class RemoveHouseFragment : Fragment() {

    private lateinit var gson: Gson
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var databaseSQL: ManagerToken

    private lateinit var recyclerViewFavorite: RecyclerView
    private lateinit var adapterFavorite: AdapterRemoveHouse
    private var listImage: MutableList<ModelRemoveHouse> = mutableListOf()

    lateinit var userProfile :UserDataSQL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_remove, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseSQL = ManagerToken(context)

        userProfile = databaseSQL.viewUserWithToken()[0]

        recyclerViewFavorite = view.findViewById(R.id.recycler_remove_house)

        listImage = mutableListOf()

        adapterFavorite = AdapterRemoveHouse(context, listImage)

        recyclerViewFavorite.adapter = adapterFavorite

        val gridLayoutManager = GridLayoutManager(context, 1)

        recyclerViewFavorite.layoutManager = gridLayoutManager

        findImageRequest()

    }
    private fun findImageRequest(){

        var url = "http://192.168.1.142:8080/users/${userProfile.idUser}"

        gson = Gson()

        val stringRequest = object: StringRequest(
            Method.GET, url,
            {
                val user = gson.fromJson(it, User::class.java)

                user.houses?.map { house ->
                    listImage.add(ModelRemoveHouse(house.idHouse, user.username, house.images!![0].url, house.region, house.price))
                }
                recyclerViewFavorite.adapter?.notifyDataSetChanged()
            }, {

            }) {
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