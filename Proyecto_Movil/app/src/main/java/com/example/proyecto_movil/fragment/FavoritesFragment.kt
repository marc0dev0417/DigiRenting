package com.example.proyecto_movil.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_movil.R
import com.example.proyecto_movil.model.Adapter.AdapterRemoveHouse
import com.example.proyecto_movil.model.ModelRemoveHouse

class FavoritesFragment : Fragment() {

    private lateinit var recyclerViewFavorite: RecyclerView
    private lateinit var adapterRemoveHouse: AdapterRemoveHouse
    private var listImage: MutableList<ModelRemoveHouse> = mutableListOf()

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }


}