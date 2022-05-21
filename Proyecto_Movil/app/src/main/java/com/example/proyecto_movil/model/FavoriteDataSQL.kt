package com.example.proyecto_movil.model

data class FavoriteDataSQL(
    var houseId: Int? = null,
    var owner: String? = null,
    var url: String? = null,
    var region: String? = null,
    var price: Double? = null,
    var keyIdUser: Int? = null
)
