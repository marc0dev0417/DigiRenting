package com.example.proyecto_movil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_movil.model.AdapterFavorite
import com.example.proyecto_movil.model.ModelFavoriteHouse

class FavoritesFrament : Fragment() {

    private lateinit var recyclerViewFavorite: RecyclerView
    private lateinit var adapterFavorite: AdapterFavorite
    private var listImage: MutableList<ModelFavoriteHouse> = mutableListOf()

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frament_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewFavorite = view.findViewById(R.id.recyclerFavorites)

        listImage = mutableListOf()

        adapterFavorite = AdapterFavorite(context, listImage)

        recyclerViewFavorite.adapter = adapterFavorite


        recyclerViewFavorite.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(context, 1)

        recyclerViewFavorite.layoutManager = gridLayoutManager

        findImageRequest()
    }

   private fun findImageRequest(){

       val listUrl: MutableList<String> = mutableListOf("https://firebasestorage.googleapis.com/v0/b/digirenting-images.appspot.com/o/houses_images%2Fcat.jfif?alt=media&token=c0df9eeb-ef1a-4fea-a9fd-f80551f0e091", "https://firebasestorage.googleapis.com/v0/b/digirenting-images.appspot.com/o/houses_images%2Fdog.jpg?alt=media&token=d8d7ca8e-ce1a-4d5b-9992-5c6fc29ca70c")

       for(url: String in listUrl){
           val modelFavoriteHouse = ModelFavoriteHouse(url)

           listImage.add(modelFavoriteHouse)
       }
       adapterFavorite.notifyDataSetChanged()
    }
}