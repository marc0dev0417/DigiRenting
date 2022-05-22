package com.example.proyecto_movil.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_movil.R
import com.example.proyecto_movil.model.Adapter.AdapterRemoveHouse
import com.example.proyecto_movil.model.Adapter.FavoritesAdapter
import com.example.proyecto_movil.model.FavoriteDataSQL
import com.example.proyecto_movil.model.ModelRemoveHouse
import com.example.proyecto_movil.sqltoken.ManagerToken

class FavoritesFragment : Fragment() {

    private lateinit var databaseSQL: ManagerToken
    private var listFavoriteSQL = mutableListOf<FavoriteDataSQL>()
    private lateinit var recyclerView: RecyclerView

    private var list = mutableListOf<FavoriteDataSQL>()
    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseSQL = ManagerToken(context)


       recyclerView = view.findViewById(R.id.recycler_favorites)
        val adapter = FavoritesAdapter(context, list)

        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapter

        findFavoriteHouse()

    }
    private fun findFavoriteHouse(){
        listFavoriteSQL = databaseSQL.viewHouseFavorite()

      if(listFavoriteSQL.size > 0) {
          Log.d("sizeListFav", listFavoriteSQL.size.toString())
          for (favorite: FavoriteDataSQL in listFavoriteSQL) {


              list.add(favorite)
          }
      }
        recyclerView.adapter?.notifyDataSetChanged()

      //  Log.d("sizeFavorite", listFavoriteSQL.size.toString() + "  ${listFavoriteSQL[0].region}")

       // recyclerView.adapter?.notifyDataSetChanged()
    }

}