package com.example.proyecto_movil.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import at.favre.lib.crypto.bcrypt.BCrypt
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.proyecto_movil.MainMenu
import com.example.proyecto_movil.R
import com.example.proyecto_movil.model.Adapter.HouseLocationsAdapter
import com.example.proyecto_movil.model.HouseLocation
import com.example.proyecto_movil.model.Token
import com.example.proyecto_movil.model.User
import com.example.proyecto_movil.model.UserDataSQL
import com.example.proyecto_movil.sqltoken.ManagerToken
import com.google.gson.Gson
import kotlin.math.abs

class HomeFragment : Fragment() {

    private lateinit var likedImageView: ImageView

    private lateinit var gson: Gson
    private lateinit var mRequestQueue: RequestQueue

    private lateinit var databaseSql: ManagerToken
    private lateinit var userDataSQL: UserDataSQL

    lateinit var locationsViewPager : ViewPager2

    lateinit var userToMutableList: MutableList<HouseLocation>

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userToMutableList = mutableListOf()

        databaseSql = ManagerToken(context)

        userDataSQL = databaseSql.viewUserWithToken()[0]

        Log.d("usernameHome", userDataSQL.username.toString())

         locationsViewPager = view.findViewById(R.id.locationsViewPager)


        findHouseAPI("http://192.168.87.136:8080/users", userDataSQL.token.toString())

        locationsViewPager.adapter = HouseLocationsAdapter(context, userToMutableList)
        locationsViewPager.clipToPadding = false
        locationsViewPager.clipChildren = false
        locationsViewPager.offscreenPageLimit = 3
        locationsViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        var compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            run {

                var r: Float = 1 - abs(position)
                page.scaleY = (0.95f + r * 0.05f)

            }
        }
        locationsViewPager.setPageTransformer(compositePageTransformer)

    }

    private fun findHouseAPI(url: String, userToken: String){
        gson = Gson()


        val stringRequest = object: StringRequest(
            Method.GET, "$url",
            { it ->
                val listUser: Array<User> = gson.fromJson(it, Array<User>::class.java)

                for(userItem in listUser){
                    for(house in userItem.houses!!){
                            userToMutableList.add(HouseLocation(house.idHouse,userItem.username, userItem.mail ,house.images!![0].url, house.images!![1].url,  house.region, house.address, house.price, house.space ,userItem.idUser))
                        for (image in house.images!!){
                            Log.d("ima", image.url.toString())
                        }
                        Log.d("ima", "---------------------------------")  //En cada card Onclick me da su indice
                    }
                }

                locationsViewPager.adapter?.notifyDataSetChanged()

            }, {

            }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val accessTokenApi: HashMap<String, String> = HashMap()

                accessTokenApi["Authorization"] = "Bearer $userToken"

                return accessTokenApi
            }
        }
        stringRequest.retryPolicy = DefaultRetryPolicy(20000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        mRequestQueue = Volley.newRequestQueue(context)
        mRequestQueue.add(stringRequest)
    }


}