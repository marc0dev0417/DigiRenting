package com.example.proyecto_movil.model

import android.text.Editable

data class House(
    var idHouse:Int? = null,
    var address: String? = null,
    var region:String? = null,
    var price:Double? = null,
    var description:String? = null,
    var space:Int? = null,
    var images: MutableList<Image>? = mutableListOf()
)