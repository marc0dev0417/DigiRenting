package com.example.proyecto_movil.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_movil.R
import com.example.proyecto_movil.model.Adapter.AdapterRemoveHouse
import com.example.proyecto_movil.model.ModelRemoveHouse

class RemoveHouseFragment : Fragment() {

    private lateinit var recyclerViewFavorite: RecyclerView
    private lateinit var adapterFavorite: AdapterRemoveHouse
    private var listImage: MutableList<ModelRemoveHouse> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_remove, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerViewFavorite = view.findViewById(R.id.recycler_remove_house)

        listImage = mutableListOf()

        adapterFavorite = AdapterRemoveHouse(context, listImage)

        recyclerViewFavorite.adapter = adapterFavorite

        val gridLayoutManager = GridLayoutManager(context, 1)

        recyclerViewFavorite.layoutManager = gridLayoutManager

        findImageRequest()

    }
    private fun findImageRequest(){

        val listUrl: MutableList<String> = mutableListOf("https://firebasestorage.googleapis.com/v0/b/digirenting-images.appspot.com/o/houses_images%2Fimage%3A15912?alt=media&token=a91b6243-e69f-4f01-a73c-529e4833c6ac", "https://firebasestorage.googleapis.com/v0/b/digirenting-images.appspot.com/o/houses_images%2Fimage%3A15912?alt=media&token=a91b6243-e69f-4f01-a73c-529e4833c6ac", "https://firebasestorage.googleapis.com/v0/b/digirenting-images.appspot.com/o/houses_images%2Fimage%3A15912?alt=media&token=a91b6243-e69f-4f01-a73c-529e4833c6ac")

        for(url: String in listUrl){
            val modelFavoriteHouse = ModelRemoveHouse(url)

            listImage.add(modelFavoriteHouse)

        }
        adapterFavorite.notifyDataSetChanged()
    }
}