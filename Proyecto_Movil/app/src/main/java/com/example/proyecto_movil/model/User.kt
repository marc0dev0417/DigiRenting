package com.example.proyecto_movil.model

data class User(
    var idUser:Int? = null,
    var firstname:String? = null,
    var lastname:String? = null,
    var address:String? = null,
    var username:String? = null,
    var mail:String? = null,
    var password:String? = null,
    var houses: MutableList<House>? = mutableListOf(),
    var houseLikes: MutableList<House>? = mutableListOf()
)
